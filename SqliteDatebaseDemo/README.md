# 前言  
将数据保存到数据库是重复或结构化数据的理想选择，例如联系人信息。

>   提醒：虽然这些API功能强大，但它们相当低级，需要花费大量时间和精力来使用
>   - 原始SQL查询没有编译时验证。随着数据图更改，您需要手动更新受影响的SQL查询。这个过程很耗时且容易出错。
>   - 您需要使用大量样板代码才能在SQL查询和数据对象之间进行转换。  
>
>   出于这些原因，Google强烈我们建议使用 [Room Persistence Library](https://developer.android.com/training/basics/data-storage/room/index.html) 作为抽象层来访问应用程序的SQLite数据库中的信息。

## 1. SQLite简介  
SQLite，是一款轻型的数据库。它是一款轻型的数据库，而且目前已经在很多嵌入式产品中使用了它，它占用资源非常的低，在嵌入式设备中，可能只需要几百K的内存就够了。

## 2. 定义数据库及字段
>   注意：通过实现BaseColumns 接口，你的内部类可以继承一个主键字段，称为_ID

以下是数据库中表明和字段

```
public final class FeedReaderContract {
    private FeedReaderContract() {}

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "entry";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
    }
}
```
## 3. 使用SQLiteOpenHelper创建数据库
- SQLiteOpenHelper类中主要方法介绍

方法名 | 作用 |
---|---
onCreate() | 创建数据库
onUpgrade() | 升级数据库
onDowngrade() |降级数据库

- 一旦你定义了数据库的外观，你应该实现创建和维护数据库和表的方法。以下是一些创建和删除表的典型语句：

```
private static final String SQL_CREATE_ENTRIES =
        "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

private static final String SQL_DELETE_ENTRIES =
        "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;
```
就像您保存在设备内部存储中的文件一样，Android会将您的数据库存储在应用的私人文件夹中。您的数据是安全的，因为默认情况下，其他应用或用户无法访问此区域。

SQLiteOpenHelper类包含一组用于管理数据库的有用API。当您使用此类获取对数据库的引用时，系统仅在需要时才执行创建和更新数据库的操作，而不是在应用程序启动过程中。您只需调用getWritableDatabase（）或getReadableDatabase（）即可。

>   注意：因为它们可以长时间运行，请确保在后台线程中调用getWritableDatabase（）或getReadableDatabase（），例如使用AsyncTask或IntentService。

- 下面SQLiteOpenHelper是使用上面显示的一些命令的一个实现：

```
public class FeedReaderDbHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + " (" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "FeedReader.db";

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
```

## 增
通过将ContentValues 对象传递给insert()方法来将数据插入到数据库中：

```
public void insert(View view) {
    SQLiteDatabase database = mDbHelper.getWritableDatabase();
    ContentValues values = new ContentValues();
    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE, "news");
    values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_SUBTITLE, "detail");
    long index = database.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, values);
    databaseInfoView.setText(String.valueOf(index) + "has inserted");
}
```

>   insert（）的第一个参数就是表名。
>
>   第二个参数告诉框架在ContentValues为空（即，您没有放置任何值）的情况下该怎么做。如果指定列的名称，框架将插入一行并将该列的值设置为空。如果您指定null，就像在此代码示例中一样，那么当没有值时，框架不会插入行。
>
>   insert（）方法返回新创建行的ID，或者如果插入数据时发生错误，则返回-1。如果您与数据库中预先存在的数据发生冲突，就会发生这种情况。

## 查
要从数据库读取数据，请使用query（）方法，并将它传递给您的选择条件和所需的列。该方法结合了insert（）和update（）的元素，除了列表定义了要获取的数据（“投影”），而不是要插入的数据。查询的结果在Cursor对象中返回给您。


```
public void query(View view) {
    SQLiteDatabase database = mDbHelper.getReadableDatabase();
    String[] projection = new String[]{FeedEntry._ID,
            FeedEntry.COLUMN_NAME_TITLE, FeedEntry.COLUMN_NAME_SUBTITLE};
    String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
    String[] selectionArgs = { "news" };
    Cursor cursor = database.query(FeedEntry.TABLE_NAME, projection, selection,
            selectionArgs, null, null, null);
    StringBuilder stringBuilder = new StringBuilder();
    while (cursor.moveToNext()) {
        stringBuilder.append(cursor.getString(0)).append(" ,")
                .append(cursor.getString(1)).append(" ,")
                .append(cursor.getString(2)).append("\n");
    }
    databaseInfoView.setText(stringBuilder.toString());
    cursor.close();
}
```

第三个和第四个参数（selection和selectionArgs）被组合起来创建一个WHERE子句。由于参数是与选择查询分开提供的，因此它们在组合之前会被转义。这使您的选择语句免于SQL注入。有关所有参数的更多详细信息，请参阅query（）参考。

要查看游标中的某一行，请使用游标移动方法之一，在开始读取值之前，您必须始终调用它。由于游标从位置-1开始，因此调用moveToNext（）将“读取位置”放置在结果中的第一个条目上，并返回游标是否已经超过结果集中的最后一个条目。对于每一行，您都可以通过调用其中一个Cursor get方法（如getString（）或getLong（））来读取列的值。对于每个get方法，您必须通过调用getColumnIndex（）或getColumnIndexOrThrow（）可以获得的列的索引位置。在遍历结果完成后，请在光标上调用close（）以释放其资源。例如，下面显示了如何获取存储在光标中的所有项目ID并将它们添加到列表中。

## 删

要从表中删除行，您需要提供标识delete（）方法的行的选择标准。该机制与query（）方法的选择参数相同。它将选择规范划分为选择条款和选择参数。该子句定义要查看的列，并且还允许您组合列测试。参数是要测试的值，这些值被绑定到该子句中。因为结果不像常规SQL语句那样处理，所以它不受SQL注入的影响。


```
public void delete(View view) {
    SQLiteDatabase database = mDbHelper.getWritableDatabase();
    // Define 'where' part of query.
    String selection = FeedEntry._ID + " = ?";
    // Specify arguments in placeholder order.
    String[] selectionArgs = {"2"};
    // Issue SQL statement.
    int deletedRows = database.delete(FeedEntry.TABLE_NAME, selection, selectionArgs);
}
```

## 改

当您需要修改数据库值的子集时，请使用update（）方法。

更新表将insert（）的ContentValues语法与delete（）的WHERE语法组合在一起。


```
public void update(View view) {
    SQLiteDatabase db = mDbHelper.getWritableDatabase();

    // New value for one column
    String title = "MyNewTitle";
    ContentValues values = new ContentValues();
    values.put(FeedEntry.COLUMN_NAME_TITLE, title);

    // Which row to update, based on the title
    String selection = FeedEntry.COLUMN_NAME_TITLE + " LIKE ?";
    String[] selectionArgs = {"news"};

    int count = db.update(FeedEntry.TABLE_NAME, values, selection, selectionArgs);
}
```
update（）方法的返回值是数据库中受影响的行数。


>   警告：由于getWritableDatabase（）和getReadableDatabase（）在关闭数据库时调用代价很高，因此只要可能需要访问数据库连接，就应该打开数据库连接。通常，关闭调用Activity的onDestroy（）中的数据库是最佳选择。




