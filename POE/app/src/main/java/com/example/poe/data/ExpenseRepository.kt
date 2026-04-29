//Repository pattern implementation for data layer
companion object {
    @Volatile
    private var INSTANCE: AppDatabase? = null

    fun getDatabase(context: Context): AppDatabase {
        return INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "app_database"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}
//Adding expense CRUD operations (insert, update, delete, get all)
//Adding category operations (insert, get all categories)
//Adding budget goal operations (insert, update, delete, get by month, get all)