package com.frick.maximilian.coffeetime.framework.data;

import com.frick.maximilian.coffeetime.framework.models.Group;
import com.frick.maximilian.coffeetime.framework.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DatabaseBO {
   public static final String GROUPS = "groups";
   private static final String MEMBERS = "members";
   private static final String USERS = "users";
   private final DatabaseReference groups;
   private final DatabaseReference users;

   public DatabaseBO(DatabaseReference databaseReference) {
      groups = databaseReference.child(GROUPS);
      users = databaseReference.child(USERS);
   }

   public void addUserToDb(String uuid, User user) {
      users.child(uuid)
            .setValue(user);
   }

   public void createGroup(Group group) {
      groups.push()
            .setValue(group);
   }

   public DatabaseReference getGroupsRef() {
      return groups;
   }

   public List<User> getUsersOfGroup(String groupId) {
      final List<User> usersInGroup = new ArrayList<>();
      Query members = groups.child(groupId)
            .child(MEMBERS);
      members.addListenerForSingleValueEvent(new ValueEventListener() {
         @Override
         public void onCancelled(DatabaseError databaseError) {

         }

         @Override
         public void onDataChange(DataSnapshot dataSnapshot) {
            for (DataSnapshot member : dataSnapshot.getChildren()) {
               users.child(member.getKey())
                     .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }

                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                           User user = dataSnapshot.getValue(User.class);
                           usersInGroup.add(user);
                        }
                     });
            }
         }
      });
      return usersInGroup;
   }

   public DatabaseReference getUsersRef() {
      return users;
   }

   public void joinGroup(String groupId, String uuid) {
      groups.child(groupId)
            .child(MEMBERS)
            .child(uuid)
            .setValue(true);
      users.child(uuid)
            .child(GROUPS)
            .child(groupId)
            .setValue(true);
   }
}
