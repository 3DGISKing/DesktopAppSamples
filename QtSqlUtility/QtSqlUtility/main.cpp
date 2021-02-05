#include <QtWidgets/QApplication>

#include <qsqldatabase.h>
#include <qsqlquery.h>
#include <qdebug.h>
#include <qsqlerror.h>

int main(int argc, char *argv[])
{
	QApplication a(argc, argv);

	QSqlDatabase db = QSqlDatabase::addDatabase("QMYSQL");

	db.setHostName("localhost");
	db.setDatabaseName("phpcms");
	db.setUserName("admin");
	db.setPassword("admin");
	db.setPort(3306);

	bool ok = db.open();

	if (!ok)
		return 0;

	for (int i = 1; i < 50000; i++) {
		QSqlQuery query;

		QString title = QString::fromLocal8Bit("新华社评论员 扎实践行新时代党的组织路线——学习贯彻习近平总书记在全国组织工作会议重要讲话'");

		title = "' " + QString::number(i) + ": " + title;


		QString keyword = QString::fromLocal8Bit("'正文'");
		QString des = QString::fromLocal8Bit("'我们党一定要有新气象新作为，关键是党的建设新的伟大工程要开创新局面。”习近平总书记在全国组织工作会议上发表重要讲话，立足党和国家事业发展全局，深刻阐明新时代党的组织路线，对党的组织工作作出新部署、提出新要求，为不断提高组织工作质量和水平、把党建设得更加坚强有力提供了根本遵循、指明了实践路径"
			"党的十八大以来，以习近平同志为核心的党中央坚持和加强党的全面领导，坚持新时代党的建设总要求，坚持党要管党、从严治党，把党的政治建设摆在首位，使党在革命性锻造中更加坚强，以党的伟大自我革命推动了伟大的社会革命。实践证明，组织路线对坚持党的领导、加强党的建设、做好党的组织工作具有十分重要的意义。"
			"不断推进党的建设新的伟大工程，必须践行新时代党的组织路线——全面贯彻习近平新时代中国特色社会主义思想，以组织体系建设为重点，着力培养忠诚干净担当的高素质干部，着力集聚爱国奉献的各方面优秀人才，坚持德才兼备、以德为先、任人唯贤，为坚持和加强党的全面领导、坚持和发展中国特色社会主义提供坚强组织保证。"
			"这一组织路线，进一步深化了对共产党执政规律、马克思主义政党建设规律的认识，既是理论的，也是实践的，必须坚定不移地贯彻落实'");


		QString url = "'http://localhost/index.php?m=content&c=index&a=show&catid=6&id=" + QString::number(i) + "'";

		QString qry = QString("INSERT INTO v9_news (id,    catid,   typeid,   title,   style,   thumb,   keywords,   description,   posids,   url,   listorder,   status,   sysadd,   islink,   username,   inputtime,   updatetime) VALUES"
			" (%1,  %2,  %3,  %4,  %5,  %6,  %7,  %8,  %9,  %10,  %11,  %12,  %13,  %14,  %15,  %16,  %17)")
			.arg(i)
			.arg(6)
			.arg(0)
			.arg(title)
			.arg("''")
			.arg("''")
			.arg(keyword)
			.arg(des)
			.arg(0)
			.arg(url)
			.arg(0)
			.arg(99)
			.arg(1)
			.arg(0)
			.arg("'phpcms'")
			.arg("'1530755025'")
			.arg("'1530755064'");

		if (!query.exec(qry))
		{
			qDebug() << "error" << i << "th" << query.lastError().text();
			qDebug() << query.executedQuery();
		}
		else
			qDebug() << "ok" << i << "th";
	}


	return a.exec();
}
