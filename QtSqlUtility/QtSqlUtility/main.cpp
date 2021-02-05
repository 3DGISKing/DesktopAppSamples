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

		QString title = QString::fromLocal8Bit("�»�������Ա ��ʵ������ʱ��������֯·�ߡ���ѧϰ�᳹ϰ��ƽ�������ȫ����֯����������Ҫ����'");

		title = "' " + QString::number(i) + ": " + title;


		QString keyword = QString::fromLocal8Bit("'����'");
		QString des = QString::fromLocal8Bit("'���ǵ�һ��Ҫ������������Ϊ���ؼ��ǵ��Ľ����µ�ΰ�󹤳�Ҫ�����¾��档��ϰ��ƽ�������ȫ����֯���������Ϸ�����Ҫ���������㵳�͹�����ҵ��չȫ�֣���̲�����ʱ��������֯·�ߣ��Ե�����֯���������²��������Ҫ��Ϊ���������֯����������ˮƽ���ѵ�����ø��Ӽ�ǿ�����ṩ�˸�����ѭ��ָ����ʵ��·��"
			"����ʮ�˴���������ϰ��ƽͬ־Ϊ���ĵĵ������ֺͼ�ǿ����ȫ���쵼�������ʱ�����Ľ�����Ҫ�󣬼�ֵ�Ҫ�ܵ��������ε����ѵ������ν��������λ��ʹ���ڸ����Զ����и��Ӽ�ǿ���Ե���ΰ�����Ҹ����ƶ���ΰ�����������ʵ��֤������֯·�߶Լ�ֵ����쵼����ǿ���Ľ��衢���õ�����֯��������ʮ����Ҫ�����塣"
			"�����ƽ����Ľ����µ�ΰ�󹤳̣����������ʱ��������֯·�ߡ���ȫ��᳹ϰ��ƽ��ʱ���й���ɫ�������˼�룬����֯��ϵ����Ϊ�ص㣬���������ҳϸɾ������ĸ����ʸɲ����������۰������׵ĸ����������˲ţ���ֵ²ż汸���Ե�Ϊ�ȡ�����Ψ�ͣ�Ϊ��ֺͼ�ǿ����ȫ���쵼����ֺͷ�չ�й���ɫ��������ṩ��ǿ��֯��֤��"
			"��һ��֯·�ߣ���һ����˶Թ�����ִ�����ɡ����˼��������������ɵ���ʶ���������۵ģ�Ҳ��ʵ���ģ�����ᶨ���Ƶع᳹��ʵ'");


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
