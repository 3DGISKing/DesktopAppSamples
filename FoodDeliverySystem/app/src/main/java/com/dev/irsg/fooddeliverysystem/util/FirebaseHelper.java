package com.dev.irsg.fooddeliverysystem.util;

/**
 * Created by Ugi on 6/13/2017.
 */

import android.app.Activity;
import android.widget.Toast;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import com.dev.irsg.fooddeliverysystem.entities.User;

public class FirebaseHelper {
    public final static String CUSTOMERS_ROOT = "customers";
    public final static String ADMIN_ROOT = "admins";

    private static FirebaseHelper instance;

    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static FirebaseAuth     auth = FirebaseAuth.getInstance();

    Transition transition;

    private FirebaseHelper() {
        database.getReference().child("Foods")
                .addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                           String key = snapshot.getKey();

                            for (DataSnapshot childsnapshot : snapshot.getChildren()) {

                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public static FirebaseHelper getInstance() {
        if (instance == null)
            instance = new FirebaseHelper();

        return instance;
    }

    public void writeCustomerProfile(final User user) {
        Query query = database.getReference().child(CUSTOMERS_ROOT).orderByChild("email").equalTo(user.getEmail()).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    transition.setToast("This email is already in use. Want to login?");
                    return;
                } else {
                    DatabaseReference newuserref = database.getReference().child(CUSTOMERS_ROOT).push();

                    database.getReference().child(CUSTOMERS_ROOT).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User newuser = dataSnapshot.getValue(User.class);

                            if(user.equals(newuser)) {
                                transition.setToast("New profile created.");
                                transition.transit();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    newuserref.setValue(user);
                }

             }



            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void writeAdminProfile(final User user) {
        Query query = database.getReference().child(ADMIN_ROOT).orderByChild("email").equalTo(user.getEmail()).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    transition.setToast("This email is already in use. Want to login?");
                    return;
                } else {
                    DatabaseReference newuserref = database.getReference().child(ADMIN_ROOT).push();

                    database.getReference().child(ADMIN_ROOT).addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                            User newuser = dataSnapshot.getValue(User.class);

                            if(user.equals(newuser)) {
                                transition.setToast("New profile created.");
                                transition.transit();
                            }
                        }

                        @Override
                        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onChildRemoved(DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    newuserref.setValue(user);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    public void setTransition(Transition r) {
        transition = r;
    }
}
