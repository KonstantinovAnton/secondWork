package com.example.secondwork;


import android.os.Parcel;
import android.os.Parcelable;

public class Persons implements Parcelable {

    public Integer id_person;
    public String fname;
    public String lname;
    public String image;


    protected Persons(Parcel parcel) {
        id_person = parcel.readInt();
        fname = parcel.readString();
        lname = parcel.readString();
        image = parcel.readString();
    }



    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id_person);
        parcel.writeString(fname);
        parcel.writeString(lname);
        parcel.writeString(image);
    }

    @Override
    public int describeContents() {
        return 0;
    }


    public static final Creator<Persons> CREATOR = new Creator<Persons>() {
        @Override
        public Persons createFromParcel(Parcel in) {
            return new Persons(in);
        }

        @Override
        public Persons[] newArray(int size) {
            return new Persons[size];
        }
    };


    public void setID(int idPerson){id_person = idPerson;}
    public void setFname(String fName){fname = fName;}
    public void setLname(String lName){lname = lName;}
    public void setImg(String Img){image = Img;}


    public int getID() {
        return id_person;
    }
    public String getFname(){
        return fname;
    }
    public String getLname()
    {
        return lname;
    }
    public String getImage(){return  image;}

    public Persons(Integer id, String fn, String ln, String img)
    {
        id_person = id;
        fname = fn;
        lname = ln;
        image = img;
    }
}
