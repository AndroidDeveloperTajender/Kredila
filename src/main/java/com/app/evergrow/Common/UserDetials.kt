package com.app.evergrow.Common

data class StudentInfo(
    var studentList: ArrayList<StudentItem> = arrayListOf()
)
data class StudentItem(
    var name: String = "",
    var age: Int = 0,
    var address: String = "",
    var mobile_no: String = ""
)