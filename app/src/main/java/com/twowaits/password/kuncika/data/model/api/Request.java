package com.twowaits.password.kuncika.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class Request {

    public Request() {

    }

    public static class AppsRequest {

        @Expose @SerializedName("user_id")
        private String userId;
        @Expose @SerializedName("user_name")
        private String userName;

        public AppsRequest(String userId, String userName) {
            this.userId = userId;
            this.userName = userName;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (object == null || getClass() != object.getClass()) {
                return false;
            }

            AppsRequest that = (AppsRequest) object;

            if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
                return false;
            }
            return userName != null ? userName.equals(that.userName)
                    : that.userName == null;
        }

        @Override
        public int hashCode() {
            int result = userId != null ? userId.hashCode() : 0;
            result = 31 * result + (userName != null ? userName.hashCode() : 0);
            return result;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }
    }

}
