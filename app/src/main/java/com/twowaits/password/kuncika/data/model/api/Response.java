package com.twowaits.password.kuncika.data.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.annotation.NonNull;

public class Response {

    @Expose @SerializedName("data")
    private List<AppsResponse> data;

    public List<AppsResponse> getData() {
        return data;
    }

    public void setData(List<AppsResponse> data) {
        this.data = data;
    }

    @NonNull
    @Override
    public String toString() {
        String returned = "";
        for (AppsResponse appsResponse: data) {
            returned += appsResponse.toString();
        }
        return returned;
    }

    public static class AppsResponse {
        @Expose
        @SerializedName("id")
        private String id;
        @Expose
        @SerializedName("app_title")
        private String appTitle;
        @Expose
        @SerializedName("app_icon")
        private String appIcon;
        @Expose
        @SerializedName("app_details")
        private String appDetails;
        @Expose
        @SerializedName("app_password")
        private String appPassword;
        @Expose
        @SerializedName("app_status")
        private String appStatus;
        @Expose
        @SerializedName("app_type")
        private String appType;
        @Expose
        @SerializedName("created_at")
        private String createdAt;
        @Expose
        @SerializedName("updated_at")
        private String updatedAt;

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof AppsResponse)) {
                return false;
            }

            AppsResponse that = (AppsResponse) o;

            if (!id.equals(that.id)) {
                return false;
            }

            return appTitle != null ? appTitle.equals(that.appTitle) : that.appTitle == null;

        }

        @Override
        public int hashCode() {
            int result = appTitle.hashCode();
            result = 31 * result + id.hashCode();
            result = 31 * result + (appPassword != null ? appPassword.hashCode() : 0);
            return result;
        }

        @NonNull
        @Override
        public String toString() {
            return id + " = " + appTitle;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAppTitle() {
            return appTitle;
        }

        public void setAppTitle(String appTitle) {
            this.appTitle = appTitle;
        }

        public String getAppIcon() {
            return appIcon;
        }

        public void setAppIcon(String appIcon) {
            this.appIcon = appIcon;
        }

        public String getAppDetails() {
            return appDetails;
        }

        public void setAppDetails(String appDetails) {
            this.appDetails = appDetails;
        }

        public String getAppPassword() {
            return appPassword;
        }

        public void setAppPassword(String appPassword) {
            this.appPassword = appPassword;
        }

        public String getAppStatus() {
            return appStatus;
        }

        public void setAppStatus(String appStatus) {
            this.appStatus = appStatus;
        }

        public String getAppType() {
            return appType;
        }

        public void setAppType(String appType) {
            this.appType = appType;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }
    }
}
