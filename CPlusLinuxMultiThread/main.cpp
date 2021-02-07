#include <vector>
#include <pthread.h>
#include <unistd.h>
#include <iostream>
#include <sstream>
#include <cstdlib>

using namespace std;

//Helper function. splite string with character
template<typename Out>
int split(const string &s, char delim, Out result) {
    stringstream ss(s);

    int tokenCount = 0;

    string str;
    while (getline(ss, str, delim)) {
        *(result++) = str;
        tokenCount++;
    }

    return tokenCount;
}

struct User{
    int UserID;
    int GroupNumber;
    int AccessPosition;
    int ElapsedSecondsSincePreviousRequest;
    int DurationSecondsOfAccess;
    int ArrivedTimeOfRequest;
    int AccessState;
};

static int startingGroupNumber = -1;
static vector<User> users;
static pthread_mutex_t mutex;
static pthread_cond_t conditionStartingGroupFinished = PTHREAD_COND_INITIALIZER;
static pthread_cond_t conditionSameAccessPosition = PTHREAD_COND_INITIALIZER;
static int countOfWaitedUsersByNonStartingGroup = 0;
static int countOfWaitedUsersBySameAccessPosition = 0;

#define ACCESS_NOSTARTED  0
#define ACCESS_STARTED  1
#define ACCESS_FINISHED 2

bool allStartingGroupUsersAccessFinished() {
    for(unsigned int i = 0; i < users.size(); i++) {
        if (users.at(i).GroupNumber == startingGroupNumber && users.at(i).AccessState != ACCESS_FINISHED)
            return false;
    }

    return true;
}

int firstUserIdOfSameAccessPosition(int position, int userID) {
    for(unsigned int i = 0; i < users.size(); i++) {
        if(users.at(i).UserID == userID)
            continue;

        if(users.at(i).AccessState == ACCESS_NOSTARTED)
            continue;

        if(users.at(i).AccessState == ACCESS_FINISHED)
            continue;

        if (users.at(i).AccessPosition == position)
            return users.at(i).UserID;
    }

    return -1;
}

int nonStartingGroupNumber() {
    if (startingGroupNumber == 1)
        return 2;
    else
        return 1;
}

void *accessDatabase(void * data) {
    User* user = reinterpret_cast<User*>(data);

    pthread_mutex_lock(&mutex);

    cout<<"User "<<user->UserID<<" from Group "<<user->GroupNumber<<" arrives to the DBMS"<<endl;

    if( user->GroupNumber != startingGroupNumber) {
        if(!allStartingGroupUsersAccessFinished()) {
            countOfWaitedUsersByNonStartingGroup++;
            cout<<"User "<<user->UserID<<" is waiting due to its group"<<endl;
            pthread_cond_wait(&conditionStartingGroupFinished, &mutex);
        }
    }

    int userId = firstUserIdOfSameAccessPosition(user->AccessPosition, user->UserID);

    if(userId != -1) {
        countOfWaitedUsersBySameAccessPosition++;
        cout<<"User "<<user->UserID<<" is waiting: position "<< user->AccessPosition<<" of the database is being used by user "<<userId<<endl;
        pthread_cond_wait(&conditionSameAccessPosition, &mutex);
    }

    user->AccessState = ACCESS_STARTED;
    cout<<"User "<<user->UserID<<" is accessing the position "<<user->AccessPosition<<" of the database for "<<user->DurationSecondsOfAccess<<" second(s)"<<endl;

    pthread_mutex_unlock(&mutex);

    /*
     * Request time: it is the request’s duration in seconds (use
       the sleep function to simulate this time).
     */

    sleep(static_cast<unsigned int>(user->DurationSecondsOfAccess));

    pthread_mutex_lock(&mutex);
    user->AccessState = ACCESS_FINISHED;
    cout<<"User "<<user->UserID<<" finished its execution"<<endl;

    pthread_cond_signal(&conditionSameAccessPosition);

    if(user->GroupNumber == startingGroupNumber && allStartingGroupUsersAccessFinished()) {
        pthread_cond_broadcast(&conditionStartingGroupFinished);

        cout<<endl;
        cout<<"All users from Group"<<startingGroupNumber<<" finished their execution"<<endl;
        cout<<"The users from Group"<<nonStartingGroupNumber()<<" start their execution"<<endl;
        cout<<endl;
    }

    pthread_mutex_unlock(&mutex);

    return NULL;
}

bool parseInputFileFromStream() {
    string line;

   int lineNumber = 0;
   int arrivedTime = 0;
   int userId = 1;

    while(!cin.eof() && getline(cin, line)){
        string tokens[100];

        int tokenCount = split(line, ' ', tokens);

        if(lineNumber == 0) {
            startingGroupNumber = atoi (tokens[0].c_str());
        }
        else {
            User user;

            user.GroupNumber = atoi(tokens[0].c_str());
            user.AccessPosition = atoi(tokens[1].c_str());
            user.ElapsedSecondsSincePreviousRequest = atoi(tokens[2].c_str());
            user.DurationSecondsOfAccess = atoi(tokens[3].c_str());
            user.ArrivedTimeOfRequest = arrivedTime;
            user.UserID = userId;
            user.AccessState = ACCESS_NOSTARTED;

            userId++;
            arrivedTime += user.ElapsedSecondsSincePreviousRequest;


            if(user.GroupNumber != 1 && user.GroupNumber != 2) {
                cout<<"user group number should be 1 or 2";
                return false;
            }

            users.push_back(user);
        }

        lineNumber++;

    }

    return true;
}

int main(int argc, char **argv)
{
    if (isatty(0)){
        std::cout << "Standard input was not redirected\n";
        return 1;
    }

    if (!parseInputFileFromStream()){
        cout<<"failed to parse input file"<<endl;
        return -1;
    }

    pthread_mutex_init(&mutex, NULL); // Initialize access to 1

    pthread_t userThreadIds[users.size()];

    for(unsigned int i = 0; i < users.size(); i++) {
       void * data = reinterpret_cast<void*>(&users.at(i));

       /*
        * Request arrival time: it represents the elapsed amount of
          time (seconds) since the arrival of the previous request
          (use the sleep function to simulate this time).
        */

       sleep(static_cast<unsigned int>(users.at(i).ElapsedSecondsSincePreviousRequest));

       if(pthread_create(&userThreadIds[i], NULL, accessDatabase, data)) {
           cout<<("failed to create thread");
           return 1;
       }
    }

    // Wait for the threads to finish.

    for (int i = 0; i < users.size(); i++)
        pthread_join(userThreadIds[i], NULL);

    int group1RequestCount = 0;
    int group2RequestCount = 0;

    for(unsigned int i = 0; i < users.size();i++) {
        if(users.at(i).GroupNumber == 1)
            group1RequestCount++;

        if(users.at(i).GroupNumber == 2)
            group2RequestCount++;
    }

    cout<<endl;

    cout<<"Total Requests:"<<endl;
    cout<<"    Group 1: "<<group1RequestCount<<endl;
    cout<<"    Group 2: "<<group2RequestCount<<endl;

    cout<<endl;
    cout<<"Requests that waited:"<<endl;
    cout<<"    Due to its group: "<<countOfWaitedUsersByNonStartingGroup<<endl;
    cout<<"    Due to a locked position: "<<countOfWaitedUsersBySameAccessPosition<<endl;

    return 0;
}
