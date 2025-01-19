package com.example.fieldwise.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.fieldwise.data.local.entities.Course

interface CourseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCourse(course: Course)

    @Delete
    fun deleteCourse(course: Course)
}