package com.example.secondwork;


import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {

    public Integer ID;
    public String fname;
    public String lname;
    public String image;


    protected Person(Parcel parcel) {
        ID = parcel.readInt();
        fname = parcel.readString();
        lname = parcel.readString();
        image = parcel.readString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(ID);
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(image);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };


    public void setID(Integer i){ID = i;}

    public void setFname(String fn){fname = fn;}
    public void setLname(String ln){lname = ln;}
    public void setImg(String img){image = img;}


    public int getID() {
        return ID;
    }
    public String getFname(){
        return fname;
    }
    public String getLname()
    {
        return lname;
    }
    public String getImage(){return  image;}




    public Person(Integer id, String fn, String ln, String img)
    {
        ID = id;
        fname = fn;
        lname = ln;
        image = img;
    }
}
