package com.example.lancer;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CLIENT 
	                + " TEXT," + KEY_LOCATION + " INTEGER" +
                ")";
        String CREATE_TASKS_TABLE = 
        		"CREATE TABLE " + TABLE_TASKS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME 
	                + " TEXT," + KEY_JOB + " INTEGER," 
	                + KEY_DEADLINE + " TEXT," + KEY_LOCATION + " INTEGER" +
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
	                + " TEXT," + KEY_PRICE + " TEXT" +
                ")";
        String CREATE_EXPENSES_TABLE = 
        		"CREATE TABLE " + TABLE_EXPENSES + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_NAME 
	                + " TEXT," + KEY_JOB + " INTEGER," 
	                + KEY_TASK + " INTEGER," + KEY_ITEM + " INTEGER," 
	        		+ KEY_QUANTITY +
                ")";
        String CREATE_LOCATIONS_TABLE = 
        		"CREATE TABLE " + TABLE_LOCATIONS + 
        		"("
	                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_LOCATION 
	                + " TEXT," + KEY_ADD1 + " TEXT," + KEY_ADD2 + " TEXT," 
	        		+ KEY_ADD3 + " TEXT" +
                ")";
        
        //execute the statements
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
        int location;
        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { "'"+job.getLocation()+"'"}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        location = Integer.parseInt((cursor.getString(0)));
        values.put(KEY_CLIENT, job.getClient());
        values.put(KEY_LOCATION, location);
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
    
    public void addTask(Task task)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int location, job;
        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { task.getLocation()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        location = Integer.parseInt((cursor.getString(0)));
        cursor = db.query(TABLE_JOBS, new String[] { KEY_ID }, KEY_CLIENT + "=?",
                new String[] { task.getJob()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        job = Integer.parseInt((cursor.getString(0)));
        values.put(KEY_NAME, task.getName());
        values.put(KEY_JOB, job);
        values.put(KEY_DEADLINE, task.getDeadline());
        values.put(KEY_LOCATION, location);
        try
        {
        	db.insertOrThrow(TABLE_TASKS, null, values);
        }
        catch(Exception e)
        {
        	db.close();
        }
        db.close();
    }
    
    public void addNote(Note note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int task, job;
        Cursor cursor = db.query(TABLE_JOBS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { note.getJob()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        job = Integer.parseInt((cursor.getString(0)));
        cursor = db.query(TABLE_TASKS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { note.getTask()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        task = Integer.parseInt((cursor.getString(0)));
        values.put(KEY_JOB, job);
        values.put(KEY_TASK, task);
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
        int task, job, item;
        Cursor cursor = db.query(TABLE_JOBS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { expense.getJob()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        job = Integer.parseInt((cursor.getString(0)));
        cursor = db.query(TABLE_TASKS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { expense.getTask()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        task = Integer.parseInt((cursor.getString(0)));
        cursor = db.query(TABLE_ITEMS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { expense.getItem()}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        item = Integer.parseInt((cursor.getString(0)));
        values.put(KEY_JOB, job);
        values.put(KEY_TASK, task);
        values.put(KEY_ITEM, item);
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
        	Log.v("No worky", e.toString());
        	db.close();
        }
        db.close();
    }
 
    //a method that returns a single job
    Job getJob(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = 
        		"SELECT " + TABLE_JOBS + "." + KEY_ID + ", " + TABLE_JOBS + "." + KEY_CLIENT + ", " + 
        		TABLE_LOCATIONS + "." + KEY_LOCATION + " FROM " + TABLE_JOBS + " JOIN " + TABLE_LOCATIONS + " ON " + TABLE_LOCATIONS + 
        		"." + KEY_ID + "=" + TABLE_JOBS + "." + KEY_LOCATION;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) cursor.moveToFirst();
        Job job = new Job(cursor.getString(1),
                cursor.getString(2));
        return job;
    }
    
    //a method that returns a single location
    Location getLocation(int id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = 
        		"SELECT " + KEY_LOCATION + " FROM " + TABLE_LOCATIONS;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) cursor.moveToFirst();
        Location location = new Location(cursor.getString(1),
                cursor.getString(2));
        return location;
    }
 
    //a method to return all jobs in the database
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
            	Job job = new Job(cursor.getString(1),
                        cursor.getString(2));

                jobList.add(job);
            } while (cursor.moveToNext());
        }
        return jobList;
    }
 
    //a method to update the data in a specified job
    public boolean updateJob(Job job) 
    {
    	boolean success = true;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int location, id = job.getId();
        Cursor cursor = db.query(TABLE_LOCATIONS, new String[] { KEY_ID }, KEY_LOCATION + "=?",
                new String[] { "'"+job.getLocation()+"'"}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        location = Integer.parseInt((cursor.getString(0)));
        values.put(KEY_CLIENT, job.getClient());
        values.put(KEY_LOCATION, location);
        try
        {
        	db.update(TABLE_JOBS, values, KEY_ID + "=?", new String[] { Integer.toString(id)});
        }
        catch(Exception e)
        {
        	success = false; //in case the job conflicts with an already present job
        }
               
        return success;
    }

    //a method to delete a specified job
    public void deleteJob(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_JOBS, KEY_ID + " = ?",
                new String[] { Integer.toString(id) });
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
        return count;
    }
    
    public int getJobTaskCount(int job)
    {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getTaskCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_TASKS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getItemCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_ITEMS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getExpenseCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getJobExpenseCount(int job)
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getTaskExpenseCount(int task)
    {
        String countQuery = "SELECT  * FROM " + TABLE_EXPENSES + " WHERE " + KEY_TASK + "=" + task;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getJobNoteCount(int job)
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_JOB + "=" + job;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public int getTaskNoteCount(int task)
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES + " WHERE " + KEY_TASK + "=" + task;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    
    public int getNoteCount()
    {
        String countQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    
    public List<String> getAllLocationStrings()
    {
    	List<String> locList = new ArrayList<String>();
    	String selectQuery = "SELECT * FROM " + TABLE_LOCATIONS;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst())
    	{
    		do
    		{
    			locList.add(cursor.getString(2));
    		}while(cursor.moveToNext());
    	}
    	return locList;
    }
    
    public List<String> getAllItemStrings()
    {
    	List<String> itemList = new ArrayList<String>();
    	String selectQuery = "SELECT * FROM " + TABLE_ITEMS;
    	SQLiteDatabase db = this.getWritableDatabase();
    	Cursor cursor = db.rawQuery(selectQuery, null);
    	if(cursor.moveToFirst())
    	{
    		do
    		{
    			itemList.add(cursor.getString(1));
    		}while(cursor.moveToNext());
    	}
    	return itemList;
    }
}
