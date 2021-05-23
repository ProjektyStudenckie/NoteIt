package com.sww.noteit.model

import java.io.Serializable

class Note :Serializable {
    public var ID: Int = 0
    public  var UserID: String =" "
    public var Title: String =" "
    public var Note: String = " "
    public var DateDate: String =" "
    public  var ImageURL: String = " "

    constructor() {}

    constructor(ID: Int,UserID:String,Title:String,Note:String,DateDate:String,ImageURL:String)
    {
        this.ID = ID
        this.UserID = UserID
        this.Title = Title
        this.Note = Note
        this.DateDate=DateDate
        this.ImageURL=ImageURL
    }

}