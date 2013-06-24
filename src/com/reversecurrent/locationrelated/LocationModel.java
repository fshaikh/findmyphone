package com.reversecurrent.locationrelated;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationModel implements Parcelable {
	public double Latitude;
	public double Longitude;
	public String MapUrl;
	public String GeocodeAddress;
	public String SenderNumber;
	
	public LocationModel()
	{
		
	}
	
	public LocationModel(Parcel in) {
		Latitude = in.readDouble();
		Longitude = in.readDouble();
		MapUrl = in.readString();
		GeocodeAddress = in.readString();
		SenderNumber = in.readString();
	}
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeDouble(Latitude);
		dest.writeDouble(Longitude);
		dest.writeString(MapUrl);
		dest.writeString(GeocodeAddress);
		dest.writeString(SenderNumber);
	}
	
	public static final Parcelable.Creator<LocationModel> CREATOR
    = new Parcelable.Creator<LocationModel>()
    {
		public LocationModel createFromParcel(Parcel in) {
		    return new LocationModel(in);
		}

		public LocationModel[] newArray(int size) {
		    return null;
		}
    };
}
