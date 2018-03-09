package com.frick.maximilian.coffeetime.models;

import com.google.gson.annotations.SerializedName;

public class Group {
   private String id;
   @SerializedName ("public")
   private boolean isPublic;
   @SerializedName ("name")
   private String name;

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
