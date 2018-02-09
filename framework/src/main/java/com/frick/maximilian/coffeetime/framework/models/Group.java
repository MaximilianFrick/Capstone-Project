package com.frick.maximilian.coffeetime.framework.models;

public class Group {
   private String id;
   private boolean isPublic;
   private String name;

   public Group(String name) {
      this.name = name;
   }

   public Group() {
   }

   public Group(String groupName, boolean isPublic) {
      this.name = groupName;
      this.isPublic = isPublic;
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

   public boolean isPublic() {
      return isPublic;
   }

   public void setPublic(boolean aPublic) {
      isPublic = aPublic;
   }
}
