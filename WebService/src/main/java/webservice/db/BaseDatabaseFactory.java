package webservice.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.table.TableUtils;
import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import webservice.exception.DbSqlException;
import webservice.io.IOUtil;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class BaseDatabaseFactory {

    private static final String connectionString = "jdbc:sqlite:" + getDbFilePath();
    protected static ConnectionSource connectionSource;
    protected static boolean testInited = false;

    protected BaseDatabaseFactory() {
    }

    private static String getDbFilePath() {
        File databaseFile = new File(IOUtil.getFilePathUnderRootDirOfJarFileOrClassDir("/database.db"));
        if (!databaseFile.exists()) {
            try {
                databaseFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return databaseFile.getAbsolutePath();

    }

    protected static <Po, PK> Dao<Po, PK> createDao(Class<Po> clazz) {
        try {
            return DaoManager.createDao(connectionSource, clazz);
        } catch (SQLException e) {
            throw new DbSqlException(e);
        }
    }

    private static void basicInit() throws SQLException {
        BaseDatabaseFactory.connectionSource = new JdbcConnectionSource(connectionString);
    }

    public static void init() throws SQLException {
        basicInit();
        initializeTables();
        initializeData();

    }

    public static void initTest() throws SQLException {
        if (!testInited) {
            basicInit();
            dropAllTables();
            testInited = true;
        }

    }

    public static void dropAllTables() {
        FastClasspathScanner scanner = new FastClasspathScanner();
        scanner.matchClassesWithAnnotation(DatabaseTable.class, classWithAnnotation -> {
            try {
                TableUtils.dropTable(connectionSource, classWithAnnotation, true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).scan();
        initializeTables();
    }

    private static void initializeTables() {
        FastClasspathScanner scanner = new FastClasspathScanner();

        scanner.matchClassesWithAnnotation(DatabaseTable.class, classWithAnnotation -> {
            try {
//                ServerLogServiceFactory.getService().printLog("BaseDatabaseFactory", "initialized a table with Po class " + classWithAnnotation.getName());
                TableUtils.createTableIfNotExists(connectionSource, classWithAnnotation);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).scan();


    }

    private static void initializeData() {

//        try {
//            Dao<BankAccountPo, Integer> dao = createDao(BankAccountPo.class);
//            TableUtils.dropTable(dao, true);
//            TableUtils.createTableIfNotExists(connectionSource, BankAccountPo.class);
//            dao.create(new BankAccountPo("李泽言", 10000, new Date()));
//            dao.create(new BankAccountPo("许墨", 2000, new Date()));
//
//            Dao<FinanceStaffPo, String> financeStaffDao = createDao(FinanceStaffPo.class);
//            TableUtils.dropTable(financeStaffDao, true);
//            TableUtils.createTableIfNotExists(connectionSource, FinanceStaffPo.class);
//            financeStaffDao.create(new FinanceStaffPo("10002", "财务经理", new Date(), "123456",EmployeeState.Active,true));
//            financeStaffDao.create(new FinanceStaffPo("10103", "财务职员", new Date(), "123456",EmployeeState.Active,false));
//
//            Dao<SaleStaffPo, String> saleStaffDao = createDao(SaleStaffPo.class);
//            TableUtils.dropTable(saleStaffDao, true);
//            TableUtils.createTableIfNotExists(connectionSource, SaleStaffPo.class);
//            saleStaffDao.create(new SaleStaffPo("10004", "销售主管", new Date(), "123456", SaleStaffType.Manager,EmployeeState.Active,true));
//            saleStaffDao.create(new SaleStaffPo("10201", "C销售主任", new Date(), "123456", SaleStaffType.Manager,EmployeeState.Active,false));
//
//            Dao<InventoryStaffPo,String> inventoryStaffPos=createDao(InventoryStaffPo.class);
//            TableUtils.dropTable(inventoryStaffPos, true);
//            TableUtils.createTableIfNotExists(connectionSource, InventoryStaffPo.class);
//            inventoryStaffPos.create(new InventoryStaffPo("10001", "123", new Date(), "123", EmployeeState.Active));
//
//            Dao<ManagerPo,String> managerPos =createDao(ManagerPo.class);
//            TableUtils.dropTable(managerPos, true);
//            TableUtils.createTableIfNotExists(connectionSource, ManagerPo.class);
//            managerPos.create(new ManagerPo("10003", "总经理", new Date(), "123456", EmployeeState.Active));
//
//            Dao<AdminPo,String> adminPos = createDao(AdminPo.class);
//            TableUtils.dropTable(adminPos, true);
//            TableUtils.createTableIfNotExists(connectionSource, AdminPo.class);
//            adminPos.create(new AdminPo("10006", "admin", new Date(), "admin", EmployeeState.Active));
//
//            Dao<CommodityPo,String>commodityPos=createDao(CommodityPo.class);
//            TableUtils.dropTable(commodityPos, true);
//            TableUtils.createTableIfNotExists(connectionSource, CommodityPo.class);
//            commodityPos.create(new CommodityPo("PRO-0002-0001","SmallLed","PRO-0002",13,new Date(),"һ",
//                    "01",34,34,34,34,100,0));
//            commodityPos.create(new CommodityPo("PRO-0002-0002","SmaqwellLed","PRO-0002",13,new Date(),"һ",
//                    "01",34,34,34,34,100,0));
//            commodityPos.create(new CommodityPo("PRO-0003-0001","SmaqweasqllLed","PRO-0003",13,new Date(),"һ",
//                    "01",34,34,34,34,100,0));
//
//            Dao<CommoditySortPo,String>commoditySortPos=createDao(CommoditySortPo.class);
//            TableUtils.dropTable(commoditySortPos, true);
//            TableUtils.createTableIfNotExists(connectionSource, CommoditySortPo.class);
//            commoditySortPos.create(new CommoditySortPo("PRO-0001","Led",0,null,null));
//            commoditySortPos.create(new CommoditySortPo("PRO-0002","DgLed",1,"PRO-0001",null));
//            commoditySortPos.create(new CommoditySortPo("PRO-0003","qweLed",1,"PRO-0001",null));
//            commoditySortPos.create(new CommoditySortPo("PRO-0004","qwasdeLed",0,"PRO-0001",null));
//
//            Dao<ClientPo,String>clientPos=createDao(ClientPo.class);
//            TableUtils.dropTable(clientPos,true);
//            TableUtils.createTableIfNotExists(connectionSource,ClientPo.class);
//            clientPos.create(new ClientPo("0",
//                    ClientType.Retailer,
//                    5,
//                    "王俊凯",
//                    "保密",
//                    "山城",
//                    "240000",
//                    "990921@qq.com",
//                    9374.7,
//                    4801.5,
//                    378,
//                    "10003",
//                    ClientState.Real));
//            clientPos.create(new ClientPo("1",
//                    ClientType.Retailer,
//                    1,
//                    "Alfred.F.Jones",
//                    "0107893",
//                    "美国华盛顿州",
//                    "110000",
//                    "17760704@twitter.com",
//                    10340,
//                    0,
//                    0,
//                    "10002",
//                    ClientState.Real));
//            clientPos.create(new ClientPo("2",
//                    ClientType.Supplier,
//                    3,
//                    "米玲",
//                    "13897331281",
//                    "恋语大学",
//                    "210000",
//                    "10010@qq.com",
//                    666,
//                    123,
//                    456,
//                    "10004",
//                    ClientState.Real));
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }


    }

}
