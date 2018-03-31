package com.frick.maximilian.coffeetime.data;

import com.frick.maximilian.coffeetime.data.models.Group;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ServerValue;
import com.google.firebase.messaging.FirebaseMessaging;

public class DatabaseBO {
   private static final String BREWING_TIME = "brewingtime";
   private static final String CUPDRINKERS = "cupdrinkers";
   private static final String GROUP = "group";
   private static final String GROUPS = "groups";
   private static final String MEMBERS = "members";
   private static final String SESSION = "session";
   private static final String STARTTIME = "starttime";
   private static final String STATUS = "status";
   private static final String USERS = "users";
   private final DatabaseReference groups;
   private final DatabaseReference users;
   private String currentGroupId;

   public DatabaseBO(DatabaseReference databaseReference) {
      groups = databaseReference.child(GROUPS);
      users = databaseReference.child(USERS);
   }

   public void addMeToSession() {
      groups.child(currentGroupId)
            .child(SESSION)
            .child(CUPDRINKERS)
            .child(getCurrentUser().getUid())
            .setValue(true);

      // Subscribe to session, so only users who wanted a cup get a final notification
      FirebaseMessaging.getInstance()
            .subscribeToTopic(currentGroupId + SESSION);
   }

   public void addUserToDb() {
      FirebaseUser user = getCurrentUser();
      users.child(user.getUid())
            .setValue(user.getDisplayName());
   }

   public void createGroup(Group group) {
      groups.push()
            .setValue(group);
   }

   public DatabaseReference getCupDrinkers() {
      return groups.child(currentGroupId)
            .child(SESSION)
            .child(CUPDRINKERS);
   }

   public String getCurrentGroup() {
      return currentGroupId;
   }

   public void setCurrentGroup(String groupId) {
      this.currentGroupId = groupId;
   }

   public DatabaseReference getGroupOfUser(String uuid) {
      return users.child(uuid)
            .child(GROUP);
   }

   public DatabaseReference getGroupsRef() {
      return groups;
   }

   public DatabaseReference getStatusRef() {
      return groups.child(currentGroupId)
            .child(SESSION)
            .child(STATUS);
   }

   public void joinGroup(String groupId) {
      String uuid = getCurrentUser().getUid();
      groups.child(groupId)
            .child(MEMBERS)
            .child(uuid)
            .setValue(true);
      users.child(uuid)
            .child(GROUP)
            .setValue(groupId);

      currentGroupId = groupId;

      // Subscribe to group for notifications
      FirebaseMessaging.getInstance()
            .subscribeToTopic(groupId);
   }

   public void leaveGroup(String groupId) {
      String uuid = getCurrentUser().getUid();
      groups.child(groupId)
            .child(MEMBERS)
            .child(uuid)
            .removeValue();
      users.child(uuid)
            .child(GROUP)
            .removeValue();

      currentGroupId = null;

      FirebaseMessaging.getInstance()
            .unsubscribeFromTopic(groupId);
   }

   public void resetSession() {
      groups.child(currentGroupId)
            .child(SESSION)
            .setValue(null);
      FirebaseMessaging.getInstance()
            .unsubscribeFromTopic(currentGroupId + SESSION);
   }

   public void setStartTime() {
      groups.child(currentGroupId)
            .child(SESSION)
            .child(STARTTIME)
            .setValue(ServerValue.TIMESTAMP);
   }

   public void setStatus(Integer coffeeStatus) {
      groups.child(currentGroupId)
            .child(SESSION)
            .child(STATUS)
            .setValue(coffeeStatus);
   }

   public void setTimeToBrew(long timeInMillis) {
      groups.child(currentGroupId)
            .child(SESSION)
            .child(BREWING_TIME)
            .setValue(timeInMillis);
   }

   public void takeACup() {
      groups.child(currentGroupId)
            .child(SESSION)
            .child(CUPDRINKERS)
            .child(getCurrentUser().getUid())
            .removeValue();
   }

   private FirebaseUser getCurrentUser() {
      return FirebaseAuth.getInstance()
            .getCurrentUser();
   }
}
