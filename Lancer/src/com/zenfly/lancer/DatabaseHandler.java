//Author: Simon McDonnell
//handles all database interaction
package com.zenfly.lancer;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper 
{
	//database info
	private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Lancer";
    //table info
    private static final String TABLE_JOBS = "jobs";
    private static final String TABLE_TASKS = "tasks";
    private static final String TABLE_NOTES = "notes";
    private static final String TABLE_ITEMS = "items";
    private static final String TABLE_EXPENSES = "expenses";
    private static final String TABLE_LOCATIONS = "locations";
    //key info
    private static final String KEY_NAME = "name";
    private static final String KEY_CLIENT = "client";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_JOB = "job";
    private static final String KEY_QUANTITY = "quantity";
    private static final String KEY_PRICE = "price";
    private static final String KEY_DEADLINE = "deadline";
    private static final String KEY_TASK = "task";
    private static final String KEY_ITEM = "item";
    private static final String KEY_ADD1 = "address1";
    private static final String KEY_ADD2 = "address2";
    private static final String KEY_ADD3 = "address3";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_BODY = "body";
	private static final String KEY_ID = "id";
	private static final String KEY_DONE = "done";
	private static final String KEY_HOURLY_WAGE = "hourlyWage";
	private static final String KEY_MINUTES_WORKED = "minutesWorked";
	private static final String KEY_ALARM = "alarm";
	private static final String KEY_PHONE = "phone";
	private static final String KEY_EMAIL = "email";
	private static final String KEY_TASK_STARTED = "taskStarted";
    
	public DatabaseHandler(Context context)
	{
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
	
	@Override
    public void onCreate(SQLiteDatabase db)
	{
		//ready the SQL statements to create our tables
        String CREATE_JOBS_TABLE = 
        		"CREATE TABLE " + TABLE_JOBS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CLIENT + " TEXT UNIQUE" + 
                ")";
        String CREATE_TASKS_TABLE = 
        		"CREATE TABLE " + TABLE_TASKS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME 
	                + " TEXT," + KEY_JOB + " INTEGER," 
	                + KEY_DEADLINE + " TEXT," + KEY_LOCATION + " INTEGER, " +
	                KEY_DONE + " INTEGER, " + KEY_HOURLY_WAGE + " REAL, " + KEY_PHONE + " TEXT, "
	                + KEY_EMAIL + " TEXT, " + KEY_MINUTES_WORKED + " INTEGER, " + KEY_ALARM + " INTEGER, " + KEY_TASK_STARTED + " INTEGER" +
                ")";
        String CREATE_NOTES_TABLE = 
        		"CREATE TABLE " + TABLE_NOTES + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_JOB 
	                + " INTEGER," + KEY_TASK + " INTEGER," 
	                + KEY_SUBJECT + " TEXT," + KEY_BODY + " TEXT" +
                ")";
        String CREATE_ITEMS_TABLE = 
        		"CREATE TABLE " + TABLE_ITEMS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME 
	                + " TEXT," + KEY_PRICE + " REAL" +
                ")";
        String CREATE_EXPENSES_TABLE = 
        		"CREATE TABLE " + TABLE_EXPENSES + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_JOB + " INTEGER," 
	                + KEY_TASK + " INTEGER," + KEY_ITEM + " INTEGER," 
	        		+ KEY_QUANTITY + " INTEGER" +
                ")";
        String CREATE_LOCATIONS_TABLE = 
        		"CREATE TABLE " + TABLE_LOCATIONS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LOCATION 
	                + " TEXT," + KEY_ADD1 + " TEXT," + KEY_ADD2 + " TEXT," 
	        		+ KEY_ADD3 + " TEXT" +
                ")";
        
        //execute the statements and create the tables
        db.execSQL(CREATE_JOBS_TABLE);
        db.execSQL(CREATE_TASKS_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_EXPENSES_TABLE);
        db.execSQL(CREATE_LOCATIONS_TABLE);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOBS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXPENSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATIONS);
        onCreate(db);
    }
	
    //methods for adding objects to the databases
    public void addJob(Job job)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_CLIENT, job.getClient());
        try
        {
        	db.insertOrThrow(TABLE_JOBS, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
    
    public long addTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        long success = 0;
        values.put(KEY_NAME, task.getName());
        values.put(KEY_JOB, task.getJob());
        values.put(KEY_DEADLINE, task.getDeadline());
        values.put(KEY_LOCATION, task.getLocation());
        values.put(KEY_HOURLY_WAGE, task.getWage());
        values.put(KEY_PHONE, task.getPhone());
        values.put(KEY_EMAIL, task.getEmail());
        values.put(KEY_MINUTES_WORKED, task.getMinutesWorked());
        values.put(KEY_DONE, task.getDone());
        values.put(KEY_TASK_STARTED, task.getStarted());
        try
        {
        	success = db.insertOrThrow(TABLE_TASKS, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        	return 0;
        }
        db.close();
        return success;
    }
    
    public void addNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB, note.getJob());
        values.put(KEY_TASK, note.getTask());
        values.put(KEY_SUBJECT, note.getSubject());
        values.put(KEY_BODY, note.getBody());
        try
        {
        	db.insertOrThrow(TABLE_NOTES, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
    
    public void addItem(Item item)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, item.getName());
        values.put(KEY_PRICE, item.getPrice());
        try
        {
        	db.insertOrThrow(TABLE_ITEMS, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
    
    public void addExpense(Expense expense)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB, expense.getJob());
        values.put(KEY_TASK, expense.getTask());
        values.put(KEY_ITEM, expense.getItem());
        values.put(KEY_QUANTITY, expense.getQuantity());
        try
        {
        	db.insertOrThrow(TABLE_EXPENSES, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
    
    public void addLocation(Location location)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION, location.getLocation());
        values.put(KEY_ADD1, location.getAdd1());
        values.put(KEY_ADD2, location.getAdd2());
        values.put(KEY_ADD3, location.getAdd3());
        try
        {
        	db.insertOrThrow(TABLE_LOCATIONS, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
 
    //methods for returning single objects
    public Job getJob(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_JOBS + " WHERE " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
	        Job job = new Job(cursor.getString(1));
	        db.close();
	        return job;
        }
        return null;
    }
    
    public Location getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS + " WHERE " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
	        Location location = new Location(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
	        db.close();
	        return location;
        }
        return null;
    }
    
    public Task getTask(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
        	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
        	task.setId(cursor.getInt(0));
        	db.close();
            return task;
        }
        db.close();
        return null;
    }
    
    public Expense getExpense(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_EXPENSES + " WHERE " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
        	Expense expense = new Expense(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
        	expense.setId(cursor.getInt(0));
        	db.close();
            return expense;
        }
        db.close();
        return null;
    }
    
    public Item getItem(int id)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_ITEMS + " WHERE " + KEY_ID + "=" + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
        	Item item = new Item(cursor.getString(1), cursor.getFloat(2));
        	db.close();
        	return item;
        }
        db.close();
        return null;
    }
    
    public Note getNote(int id)
    {
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_ID + "=" + id;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
        	Note note = new Note(cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4));
        	note.setId(cursor.getInt(0));
        	db.close();
        	return note;
        }
        db.close();
        return null;
    }
    
    //methods for getting multiple objects
    public List<Job> getAllJobs() 
    {
        List<Job> jobList = new ArrayList<Job>();
        String selectQuery = "SELECT  * FROM " + TABLE_JOBS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Job job = new Job(cursor.getString(1)); //creates a new job for each one returned by the database
            	job.setId(cursor.getInt(0));
                jobList.add(job); //adds new job to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
        return jobList;
    }
    
    public List<Job> getAllJobsByDeadline()
    {
    	List<Job> jobList = new ArrayList<Job>();
    	boolean found;
    	String selectQuery = "SELECT " + TABLE_JOBS + "." + KEY_ID + ", " + TABLE_JOBS + "." + KEY_CLIENT + ", " + "MIN(" + TABLE_TASKS + "." + KEY_DEADLINE + 
    			") FROM " + TABLE_JOBS + " JOIN " + TABLE_TASKS + " ON " + TABLE_JOBS + "." + KEY_ID + " = " + TABLE_TASKS + "." + KEY_JOB + " WHERE " + TABLE_TASKS + "." + KEY_DONE + "=" + 0 + " OR " + TABLE_TASKS + "." + KEY_DEADLINE + "<> ''" +
    			"GROUP BY " + TABLE_JOBS + "." + KEY_ID + " ORDER BY " + TABLE_TASKS + "." + KEY_DEADLINE + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Job job = new Job(cursor.getString(1)); //creates a new job for each one returned by the database
            	job.setId(cursor.getInt(0));
                jobList.add(job); //adds new job to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        selectQuery = "SELECT " + TABLE_JOBS + "." + KEY_ID + ", " + TABLE_JOBS + "." + KEY_CLIENT + " FROM " + TABLE_JOBS + " JOIN " + TABLE_TASKS + " ON " + TABLE_JOBS + "." + KEY_ID + " = " + TABLE_TASKS + "." + 
        KEY_JOB + " WHERE " + TABLE_TASKS + "." + KEY_DONE + "=" + 1 + " OR " + TABLE_TASKS + "." + KEY_DEADLINE + "= ''";
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	found = false;
            	Job job = new Job(cursor.getString(1)); //creates a new job for each one returned by the database
            	job.setId(cursor.getInt(0));
            	if(jobList.contains(job)) found = true;
            	if(!found)jobList.add(job); //adds new job to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return jobList;
    }
    
    public List<Task> getAllTasksByDeadline()
    {
    	List<Task> taskList = new ArrayList<Task>();
    	String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DONE + "=" + 0 + " OR " + KEY_DEADLINE + "<> ''" +
    			KEY_DEADLINE + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
            	task.setId(cursor.getInt(0));
                taskList.add(task); //adds new task to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DONE + "=" + 1 + " OR " + KEY_DEADLINE + " = " + "''";
        cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
            	task.setId(cursor.getInt(0));
                taskList.add(task); //adds new task to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return taskList;
    }
    
    public List<Job> getAllJobsByAlphaAsc()
    {
    	List<Job> jobList = new ArrayList<Job>();
    	String selectQuery = "SELECT " + KEY_ID + ", " + KEY_CLIENT + 
    			" FROM " + TABLE_JOBS + " ORDER BY " + KEY_CLIENT + " ASC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Job job = new Job(cursor.getString(1)); //creates a new job for each one returned by the database
            	job.setId(cursor.getInt(0));
                jobList.add(job); //adds new job to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return jobList;
    }
    
    public List<Job> getAllJobsByAlphaDesc()
    {
    	List<Job> jobList = new ArrayList<Job>();
    	String selectQuery = "SELECT " + KEY_ID + ", " + KEY_CLIENT + 
    			" FROM " + TABLE_JOBS + " ORDER BY " + KEY_CLIENT + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Job job = new Job(cursor.getString(1)); //creates a new job for each one returned by the database
            	job.setId(cursor.getInt(0));
                jobList.add(job); //adds new job to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return jobList;
    }
 
    public List<Location> getAllLocations()
    {
    	List<Location> locList = new ArrayList<Location>();
    	String selectQuery = "SELECT  * FROM " + TABLE_LOCATIONS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Location location = new Location(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)); //creates a new location for each one returned by the database
            	location.setId(cursor.getInt(0));
                locList.add(location); //adds new location to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return locList;
    }
    
    public List<Expense> getAllExpensesForJob(int id)
    {
    	List<Expense> expenseList = new ArrayList<Expense>();
    	String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_JOB + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Expense expense = new Expense(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)); //creates a new expense for each one returned by the database
            	expense.setId(cursor.getInt(0));
                expenseList.add(expense); //adds new expense to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return expenseList;
    }
    
    public List<Expense> getAllExpensesForTask(int id)
    {
    	List<Expense> expenseList = new ArrayList<Expense>();
    	String selectQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_TASK + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Expense expense = new Expense(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4)); //creates a new expense for each one returned by the database
            	expense.setId(cursor.getInt(0));
                expenseList.add(expense); //adds new expense to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return expenseList;
    }
      
    public List<Note> getAllNotesForJob(int id)
    {
    	List<Note> noteList = new ArrayList<Note>();
    	String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_JOB + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Note note = new Note(cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)); //creates a new expense for each one returned by the database
            	note.setId(cursor.getInt(0));
                noteList.add(note); //adds new note to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return noteList;
    }
    
    public List<Note> getAllNotesForTask(int id)
    {
    	List<Note> noteList = new ArrayList<Note>();
    	String selectQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_TASK + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Note note = new Note(cursor.getInt(1), cursor.getInt(2), cursor.getString(3), cursor.getString(4)); //creates a new expense for each one returned by the database
            	note.setId(cursor.getInt(0));
                noteList.add(note); //adds new note to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return noteList;
    }
    
    public List<Item> getAllItemsForJob(int id)
    {
    	List<Item> itemList = new ArrayList<Item>();
    	String selectQuery = "SELECT " + KEY_ITEM + " FROM " + TABLE_EXPENSES + " WHERE " + KEY_JOB + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Item item = new Item(cursor.getString(1), cursor.getFloat(2)); //creates a new item for each one returned by the database
            	item.setId(cursor.getInt(0));
                itemList.add(item); //adds new item to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return itemList;
    }
    
    public List<Item> getAllItemsForTask(int id)
    {
    	List<Item> itemList = new ArrayList<Item>();
    	String selectQuery = "SELECT " + KEY_ITEM + " FROM " + TABLE_EXPENSES + " WHERE " + KEY_TASK + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Item item = new Item(cursor.getString(1), cursor.getFloat(2)); //creates a new item for each one returned by the database
            	item.setId(cursor.getInt(0));
                itemList.add(item); //adds new item to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return itemList;
    }
    
    public List<Item> getAllItems()
    {
    	List<Item> itemList = new ArrayList<Item>();
    	String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	Item item = new Item(cursor.getString(1), cursor.getFloat(2)); //creates a new item for each one returned by the database
            	item.setId(cursor.getInt(0));
                itemList.add(item); //adds new item to the list
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return itemList;
    }
    
    public List<Task> getAllDoneTasks()
    {
    	List<Task> taskList = new ArrayList<Task>();
    	String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DONE + "=" + 1; //makes sure we only retrieve finished tasks
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst()) //makes sure we have results
    	{
    		do
    		{
    			Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
    			taskList.add(task);
    		}while(cursor.moveToNext());
    	}
    	db.close();
    	return taskList;
    }
    
    public List<Task> getAllTasksForJob(int thisJob)
    {
    	List<Task> taskList = new ArrayList<Task>();
    	String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + thisJob; //makes sure we only retrieve finished tasks
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst()) //makes sure we have results
    	{
    		do
    		{
    			Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
    			task.setId(cursor.getInt(0));
    			taskList.add(task);
    		}while(cursor.moveToNext());
    	}
    	db.close();
    	return taskList;
    }
    
    //methods for getting a task based on deadline
    public Task getNearestDeadlineTask()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_DONE + "=" + 0 + " ORDER BY " + KEY_DEADLINE + " ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
        	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
        	db.close();
        	return task;
        }
        db.close();
        return null;
    }
    
    public Task getNearestDeadlineTaskForJob(int job)
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + job + " AND " + KEY_DONE + "=" + 0 +/* " AND " + KEY_DEADLINE + "<> ''" +*/ " ORDER BY " + KEY_DEADLINE + " ASC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if(cursor.moveToFirst())
        {
        	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
        	db.close();
        	return task;
        }
        db.close();
        return null;
    }
    
    public Task getFarthestDeadlineTask()
    {
    	SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS + " ORDER BY " + KEY_DEADLINE + " DESC";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) 
        {
        	Task task = new Task(cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getFloat(6), cursor.getString(7), cursor.getString(8), cursor.getLong(9), cursor.getInt(5), cursor.getInt(10), cursor.getInt(11));
	        db.close();
	        return task;
        }
        return null;
    }
    
    //methods for updating the database
    public boolean updateJob(Job job) 
    {
    	boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int id = job.getId();
        values.put(KEY_CLIENT, job.getClient());
        try
        {
        	db.update(TABLE_JOBS, values, KEY_ID + "=?", new String[] { Integer.toString(id)});
        }
        catch(Exception e)
        {
        	success = false; //in case the job conflicts with an already present job
        }
        db.close();
        return success;
    }
    
    public boolean updateNote(Note note) 
    {
    	boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB, note.getJob());
        values.put(KEY_TASK, note.getTask());
        values.put(KEY_SUBJECT, note.getSubject());
        values.put(KEY_BODY, note.getBody());   
        try
        {
        	db.update(TABLE_NOTES, values, KEY_ID + "=?", new String[] { Integer.toString(note.getId())});
        }
        catch(Exception e)
        {
        	success = false; //in case the job conflicts with an already present job
        }
        db.close();
        return success;
    }
    
    public boolean updateTask(Task task) 
    {
    	boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB, task.getJob());
        values.put(KEY_NAME, task.getName());
        values.put(KEY_DEADLINE, task.getDeadline());
        values.put(KEY_LOCATION, task.getLocation());
        values.put(KEY_EMAIL, task.getEmail());
        values.put(KEY_PHONE, task.getPhone());
        values.put(KEY_ALARM, task.getAlarm());
        values.put(KEY_DONE, task.getDone());
        values.put(KEY_HOURLY_WAGE, task.getWage());
        values.put(KEY_MINUTES_WORKED, task.getMinutesWorked());
        
        try
        {
        	db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[] { Integer.toString(task.getId())});
        }
        catch(Exception e)
        {
        	success = false; //in case the job conflicts with an already present job
        }
        db.close();
        return success;
    }
    
    public boolean updateExpense(Expense expense) 
    {
    	boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_JOB, expense.getJob());
        values.put(KEY_TASK, expense.getTask());
        values.put(KEY_ITEM, expense.getItem());
        values.put(KEY_QUANTITY, expense.getQuantity());
        
        try
        {
        	db.update(TABLE_EXPENSES, values, KEY_ID + "=?", new String[] { Integer.toString(expense.getId())});
        }
        catch(Exception e)
        {
        	success = false; //in case the job conflicts with an already present expense
        }
        db.close();
        return success;
    }

    //methods to delete specified database items
    public void deleteJob(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JOBS, KEY_ID + " = ?", new String[] { Integer.toString(id) }); //deletes the specified job
        deleteJobAssociates(id);
        db.close();
    }
    
    public void deleteTask(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        deleteTaskAssociates(id);
        db.close();
    }
    
    public void deleteLocation(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOCATIONS, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        ContentValues values = new ContentValues();
        values.put(KEY_LOCATION, 0);
        db.update(TABLE_TASKS, values, KEY_LOCATION + "=?", new String[] { Integer.toString(id)});
        db.close();
    }
    
    public void deleteNote(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        db.close();
    }
    
    public void deleteItem(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ITEMS, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        db.delete(TABLE_EXPENSES, KEY_ITEM + " = ?", new String[] { Integer.toString(id) });
        db.close();
    }
    
    public void deleteExpense(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_EXPENSES, KEY_ID + " = ?", new String[] { Integer.toString(id) });
        db.close();
    }
    
    //deletes all database items linked to a deleted job
    public void deleteJobAssociates(int job)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TASKS, KEY_JOB + " = ?", new String[] { Integer.toString(job) }); //deletes all tasks associated with the job
        db.delete(TABLE_NOTES, KEY_JOB + " = ?", new String[] { Integer.toString(job) }); //deletes all notes associated with the job
        db.delete(TABLE_EXPENSES , KEY_JOB + " = ?", new String[] { Integer.toString(job) }); //deletes all expenses associated with the job
        db.close();
    }
    //deletes all database items linked to a specific task
    public void deleteTaskAssociates(int task)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES, KEY_TASK + " = ?", new String[] { Integer.toString(task) }); //deletes all notes associated with the job
        db.delete(TABLE_EXPENSES , KEY_TASK + " = ?", new String[] { Integer.toString(task) }); //deletes all expenses associated with the job
        db.close();
    }
    
    //updates the hours the user has worked on a particular task
    public void setTaskMinutes(int id, long minutes)
    {
    	String selectQuery = "SELECT " + KEY_MINUTES_WORKED + " FROM " + TABLE_TASKS + " WHERE " + KEY_ID + "=" + id; //gets the current hours worked
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {	
        	cursor.moveToFirst();
	        minutes += cursor.getInt(0); //adds the new hours worked to the old hours worked
	        values.put(KEY_MINUTES_WORKED, minutes);
	        db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[] {Integer.toString(id)}); //updates the table with the new value for hours worked
        }
        db.close();
    }
    
    //returns true or false if a task is done or not
    public boolean getTaskDone(int id)
    {
    	int done = 0;
    	String selectQuery = "SELECT " + KEY_DONE + " FROM " + TABLE_TASKS + " WHERE " + KEY_ID + "=" + id; //retrieves the done field from the tasks table
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null)
        {
        	cursor.moveToFirst();
        	done = cursor.getInt(0); //assigns the value of the field to a variable
        }
        db.close();
        //depending on if the task is set to done (1) or not done (0), we return true or false
        if(done == 0) return false;
        return true;
    }
    
    //sets a task as done (1) or not done (0)
    public void setTaskDone(int id, int done)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_DONE, Integer.toString(done));
        db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[] { Integer.toString(id)});
        db.close();
    }
    
    //methods for getting counts of database items
    public int getJobCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_JOBS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getJobTaskCount(int job)
    {
    	String countQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getTaskCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getItemCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getExpenseCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getJobExpenseCount(int job)
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getTaskExpenseCount(int task)
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_TASK + "=" + task;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getJobNoteCount(int job)
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    public int getTaskNoteCount(int task)
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_TASK + "=" + task;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }
    
    
    public int getNoteCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    }

    public void setTaskAlarm(int id, int set)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_ALARM, Integer.toString(set));
        db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[] { Integer.toString(id)});
        db.close();
    }
    
    public int getPercentDone(int id)
    {
    	String countTotal = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + id;
    	String countDone = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + id + " AND " + KEY_DONE + "=" + 1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countTotal, null);
        float total = cursor.getCount();
        cursor = db.rawQuery(countDone, null);
        float done = cursor.getCount();
        float result = (done*100)/total;
        if(countTotal.equals(countDone)) result = 100;
        cursor.close();
        db.close();
        return (int)result;
    }
    
    public int getTaskStarted(int id)
    {
    	String startedQuery = "SELECT " + KEY_TASK_STARTED + " FROM " + TABLE_TASKS + " WHERE " + KEY_ID + "=" + id;
    	SQLiteDatabase db = this.getReadableDatabase();
    	int started = 0;
        Cursor cursor = db.rawQuery(startedQuery, null);
        if(cursor.moveToFirst()) started = cursor.getInt(0);
        cursor.close();
        db.close();
        return started;
    }
    
    public void setTaskStarted(int id, int started)
    {
    	SQLiteDatabase db = this.getWritableDatabase();
    	ContentValues values = new ContentValues();
    	values.put(KEY_TASK_STARTED, Integer.toString(started));
        db.update(TABLE_TASKS, values, KEY_ID + "=?", new String[] { Integer.toString(id)});
        db.close();
    }
    
    public float getTotalCostForJob(int id)
    {
    	float total = 0.0f;
    	String selectQuery = "SELECT " + KEY_ITEM + ", " + KEY_QUANTITY + " FROM " + TABLE_EXPENSES + " WHERE " + KEY_JOB + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	String itemQuery = "SELECT " + KEY_PRICE + " FROM " + TABLE_ITEMS + " WHERE " + KEY_ID + " = " + cursor.getInt(0);
            	Cursor curs = db.rawQuery(itemQuery, null);
            	if (curs.moveToFirst())total += cursor.getInt(1) * curs.getFloat(0);
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return total;
    }
    
    public float getTotalCostForTask(int id)
    {
    	float total = 0.0f;
    	String selectQuery = "SELECT " + KEY_ITEM + ", " + KEY_QUANTITY + " FROM " + TABLE_EXPENSES + " WHERE " + KEY_TASK + " = " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst())
        {
            do
            {
            	String itemQuery = "SELECT " + KEY_PRICE + " FROM " + TABLE_ITEMS + " WHERE " + KEY_ID + " = " + cursor.getInt(0);
            	Cursor curs = db.rawQuery(itemQuery, null);
            	if (curs.moveToFirst())total += cursor.getInt(1) * curs.getFloat(0);
            } while (cursor.moveToNext()); //loop continues while there are results
        }
        db.close();
    	return total;
    }
    
    public ArrayList<String> getAllEmailsForJob(int job)
    {
    	ArrayList<String> emailList = new ArrayList<String>();
    	String selectQuery = "SELECT " + KEY_EMAIL + " FROM " + TABLE_TASKS;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst())
    	{
    		do
    		{
    			if(!cursor.getString(0).equals("")) emailList.add(cursor.getString(0));
    		}while(cursor.moveToNext());
    	}
    	cursor.close();
    	db.close();
    	return emailList;
    }
}
