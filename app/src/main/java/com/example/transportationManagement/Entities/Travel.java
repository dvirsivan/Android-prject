package com.example.transportationManagement.Entities;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;


import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Entity
public class Travel {

    @NonNull
    @PrimaryKey
    private String travelId = "id";
    private String startDate; //
    private String endDate; //
    private String creatingDate; //
    private String clientName;
    private String clientPhone;
    private String clientEmail;
    private RequestType status;
    private LinkedList<UserLocation> destinations;
    private UserLocation source;
    private String amountTravelers;


    public RequestType getStatus() {return status;}

    public String getStartDate() {return new String(startDate);}

    public String getEndDate() {return new String(endDate);}

    public String getCreatingDate() {return new String(creatingDate);}

    public LinkedList<UserLocation> getDestinations() {return new LinkedList<>(destinations);}

    public UserLocation getSource() {return source;}

    public HashMap<String, Boolean> getCompany() {return company;}

    public String getAmountTravelers() {return new String(amountTravelers);}

    public String getTravelId() {
        return new String(travelId);
    }

    public String getClientName() {
        return new String(clientName);
    }

    public String getClientPhone() {
        return new String(clientPhone);
    }

    public String getClientEmail() {
        return new String(clientEmail);
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public void setClientPhone(String clientPhone) {
        this.clientPhone = clientPhone;
    }

    public void setClientEmail(String clientEmail) {
        this.clientEmail = clientEmail;
    }

    public void setSource(UserLocation source) {this.source = source;}

    public void setAmountTravelers(String amountTravelers) {this.amountTravelers = amountTravelers;}

    public void setStatus(RequestType status) {this.status = status;}

    public void addDestinations(List dest){
        destinations.addAll(dest);
    }

    public void setStartDate(Date startDate) {
        DateConverter converter = new DateConverter();
        this.startDate = converter.dateToTimestamp(startDate);
    }

    public void setEndDate(Date endDate) {DateConverter converter = new DateConverter();
    this.endDate = converter.dateToTimestamp(endDate);}

    public static class LinkListConverter {
        @TypeConverter
        public LinkedList<UserLocation> fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            String[] locations = value.split(" ");
            LinkedList<UserLocation> result = new LinkedList(Arrays.asList(locations));
            /*for (int i = 0; i < locations.length; i += 2){
                UserLocation temp = new UserLocation(Double.parseDouble(locations[i]),Double.parseDouble(locations[i+1]));
                result.add(temp);
            }*/
            return result;
        }
        @RequiresApi(api = Build.VERSION_CODES.N)
        @TypeConverter
        public String asString(LinkedList<UserLocation> list) {
            if (list == null)
                return null;
            String result = list.stream().map(UserLocation::toString).collect(Collectors.joining(" "));
            return result;
        }
    }


    //@TypeConverters(RequestType.class)
    //private RequestType requestType;

    //@TypeConverters(DateConverter.class)
    //private Date travelDate;

    //@TypeConverters(DateConverter.class)
    //private Date arrivalDate;


    private HashMap<String, Boolean> company;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCreatingDate(String creatingDate) {
        this.creatingDate = creatingDate;
    }

    public void setDestinations(LinkedList<UserLocation> destinations) {
        this.destinations = destinations;
    }

    public void setCompany(HashMap<String, Boolean> company) {
        this.company = company;
    }

    public Travel() {
        Date now = new Date();
        DateConverter converter = new DateConverter();
        creatingDate = converter.dateToTimestamp(new Date(now.getTime()));
        destinations = new LinkedList<>();
    }

    public void setTravelId(@NonNull String travelId) {
        this.travelId = travelId;
    }

    public static class DateConverter {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        @TypeConverter
        public Date fromTimestamp(String date) throws ParseException {
            return (date == null ? null : format.parse(date));
        }

        @TypeConverter
        public String dateToTimestamp(Date date) {
            return date == null ? null : format.format(date);
        }
    }



    public enum RequestType {
        sent(0), accepted(1), run(2), close(3);
        private final Integer code;
        RequestType(Integer value) {
            this.code = value;
        }
        public Integer getCode() {
            return code;
        }
        @TypeConverter
        public static RequestType getType(Integer numeral) {
            for (RequestType ds : values())
                if (ds.code.equals(numeral))
                    return ds;
            return null;
        }
        @TypeConverter
        public static Integer getTypeInt(RequestType requestType) {
            if (requestType != null)
                return requestType.code;
            return null;
        }
    }




    public static class CompanyConverter {
        @TypeConverter
        public HashMap<String, Boolean> fromString(String value) {
            if (value == null || value.isEmpty())
                return null;
            String[] mapString = value.split(","); //split map into array of (string,boolean) strings
            HashMap<String, Boolean> hashMap = new HashMap<>();
            for (String s1 : mapString) //for all (string,boolean) in the map string
            {
                if (!s1.isEmpty()) {//is empty maybe will needed because the last char in the string is ","
                    String[] s2 = s1.split(":"); //split (string,boolean) to company string and boolean string.
                    Boolean aBoolean = Boolean.parseBoolean(s2[1]);
                    hashMap.put(/*company string:*/s2[0], aBoolean);
                }
            }
            return hashMap;
        }

        @TypeConverter
        public String asString(HashMap<String, Boolean> map) {
            if (map == null)
                return null;
            StringBuilder mapString = new StringBuilder();
            for (Map.Entry<String, Boolean> entry : map.entrySet())
                mapString.append(entry.getKey()).append(":").append(entry.getValue()).append(",");
            return mapString.toString();
        }
    }

    public static class UserLocationConverter {
        @TypeConverter
        public UserLocation fromString(String value) {
            if (value == null || value.equals(""))
                return null;
            double lat = Double.parseDouble(value.split(" ")[0]);
            double lang = Double.parseDouble(value.split(" ")[1]);
            return new UserLocation(lat, lang);
        }

        @TypeConverter
        public String asString(UserLocation warehouseUserLocation) {
            return warehouseUserLocation == null ? "" : warehouseUserLocation.getLat() + " " + warehouseUserLocation.getLon();
        }
    }
}

