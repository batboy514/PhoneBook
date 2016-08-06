package com.example.jethro.phonebook;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by jethro on 8/5/2016.
 */
public class FireBaseHelper {


    DatabaseReference db;
    Boolean saved=null;
    ArrayList<String> spacecraft=new ArrayList<>();


    public FireBaseHelper(DatabaseReference db)
    {
        this.db=db;
    }
    public Boolean save(Spacecraft spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }
        else
        {
            try {

                db.child("Spacecraft").push().setValue(spacecraft);
                saved=true;
            }catch (DatabaseException e)
            {
                    e.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

    public ArrayList<String> retrieve()
    {
            db.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    fetchData(dataSnapshot);
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        return spacecraft;
    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        spacecraft.clear();

        for(DataSnapshot ds: dataSnapshot.getChildren())
        {
            String name=ds.getValue(Spacecraft.class).getName();
           
            spacecraft.add(name);


        }
    }
}
