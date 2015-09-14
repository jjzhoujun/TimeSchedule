package pl.surecase.eu;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Schema;

public class MyDaoGenerator {

    public static void main(String args[]) throws Exception {
//        ##############Demo Start
//        // 1: 数据库版本号
//        // greendao:自动生成的Bean对象会放到/java-gen/greendao中
//        Schema schema = new Schema(1, "greendao");
//        Entity box = schema.addEntity("Box");
//        box.addIdProperty();
//        box.addStringProperty("name");
//        box.addIntProperty("slots");
//        box.addStringProperty("description");
//        new DaoGenerator().generateAll(schema, args[0]);
//        ###############Demo End.

        Schema schema = new Schema(1, "greendao");// package name

        initUserBean(schema);

        new DaoGenerator().generateAll(schema, args[0]);

    }

    private static void initUserBean(Schema schema) {

        // Common table
        Entity userBean = schema.addEntity("TSBox"); // Table Name Entity..
        userBean.setTableName("_tl_time_schedule"); // 可以对表重命名
        userBean.addLongProperty("id").primaryKey().index(); // Primary key, index.
        userBean.addBooleanProperty("b_finish");
        userBean.addStringProperty("s_title");
        userBean.addIntProperty("i_status");
        userBean.addStringProperty("s_start_time");
    }
}
