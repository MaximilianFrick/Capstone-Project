package com.frick.maximilian.coffeetime.data.models;

public class User {
   private String id;
   private String name;
   private String groupId;

   public String getGroupId() {
      return groupId;
   }

   public User(String name) {
      this.name = name;
   }

   public User() {
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
