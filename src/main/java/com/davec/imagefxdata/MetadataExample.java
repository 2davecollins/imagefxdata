/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.davec.imagefxdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.common.ImageMetadata.ImageMetadataItem;
import org.apache.commons.imaging.common.RationalNumber;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.imaging.formats.tiff.constants.ExifTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.GpsTagConstants;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.imaging.formats.tiff.taginfos.TagInfo;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author 2dave
 */
public class MetadataExample {

    public static void metadataExample(final File file) throws ImageReadException,
            IOException {
        // get all metadata stored in EXIF format (ie. from JPEG or TIFF).
        final ImageMetadata metadata = Imaging.getMetadata(file);
//          System.out.println("<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>");
//  
//          System.out.println(metadata);
//          System.out.println(">>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<");

        String fileName = FilenameUtils.getBaseName(file.getPath());
        System.out.println("out file " + fileName);
        File fileout = new File("Res/text/" + fileName.trim() + ".txt");
        FileWriter fr = new FileWriter(fileout, false);
       
        BufferedWriter br = new BufferedWriter(fr);
        br.write(fileName + "\n");

//  
        if (metadata instanceof JpegImageMetadata) {
            final JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;

//            System.out.println("file: " + file.getPath());
//
//            System.out.println("--------------------------------------------");

            // print out various interesting EXIF tags.
            // printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_XRESOLUTION, br);
            printTagValue(jpegMetadata, TiffTagConstants.TIFF_TAG_DATE_TIME, br);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_DATE_TIME_ORIGINAL, br);
            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_DATE_TIME_DIGITIZED, br);
            printTagValue(jpegMetadata, ExifTagConstants.EXIF_TAG_ISO, br);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_SHUTTER_SPEED_VALUE, br);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_APERTURE_VALUE, br);
            printTagValue(jpegMetadata,
                    ExifTagConstants.EXIF_TAG_BRIGHTNESS_VALUE, br);
            printTagValue(jpegMetadata,
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF, br);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LATITUDE, br);
            printTagValue(jpegMetadata,
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF, br);
            printTagValue(jpegMetadata, GpsTagConstants.GPS_TAG_GPS_LONGITUDE, br);

            br.close();
            fr.close();

            System.out.println("----------------------------------------------");

            // simple interface to GPS data
            final TiffImageMetadata exifMetadata = jpegMetadata.getExif();
            if (null != exifMetadata) {
                final TiffImageMetadata.GPSInfo gpsInfo = exifMetadata.getGPS();
                if (null != gpsInfo) {
                    final String gpsDescription = gpsInfo.toString();
                    final double longitude = gpsInfo.getLongitudeAsDegreesEast();
                    final double latitude = gpsInfo.getLatitudeAsDegreesNorth();
//
//                    System.out.println("    " + "GPS Description: "
//                            + gpsDescription);
//                    System.out.println("    "
//                            + "GPS Longitude (Degrees East): " + longitude);
//                    System.out.println("    "
//                            + "GPS Latitude (Degrees North): " + latitude);
                }
            }

            // more specific example of how to manually access GPS values
            final TiffField gpsLatitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE_REF);
            final TiffField gpsLatitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LATITUDE);
            final TiffField gpsLongitudeRefField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE_REF);
            final TiffField gpsLongitudeField = jpegMetadata.findEXIFValueWithExactMatch(
                    GpsTagConstants.GPS_TAG_GPS_LONGITUDE);
            if (gpsLatitudeRefField != null && gpsLatitudeField != null
                    && gpsLongitudeRefField != null
                    && gpsLongitudeField != null) {
                // all of these values are strings.
                final String gpsLatitudeRef = (String) gpsLatitudeRefField.getValue();
                final RationalNumber gpsLatitude[] = (RationalNumber[]) (gpsLatitudeField.getValue());
                final String gpsLongitudeRef = (String) gpsLongitudeRefField.getValue();
                final RationalNumber gpsLongitude[] = (RationalNumber[]) gpsLongitudeField.getValue();

                final RationalNumber gpsLatitudeDegrees = gpsLatitude[0];
                final RationalNumber gpsLatitudeMinutes = gpsLatitude[1];
                final RationalNumber gpsLatitudeSeconds = gpsLatitude[2];

                final RationalNumber gpsLongitudeDegrees = gpsLongitude[0];
                final RationalNumber gpsLongitudeMinutes = gpsLongitude[1];
                final RationalNumber gpsLongitudeSeconds = gpsLongitude[2];

                // This will format the gps info like so:
                //
                // gpsLatitude: 8 degrees, 40 minutes, 42.2 seconds S
                // gpsLongitude: 115 degrees, 26 minutes, 21.8 seconds E
//                System.out.println("    " + "GPS Latitude: "
//                        + gpsLatitudeDegrees.toDisplayString() + " degrees, "
//                        + gpsLatitudeMinutes.toDisplayString() + " minutes, "
//                        + gpsLatitudeSeconds.toDisplayString() + " seconds "
//                        + gpsLatitudeRef);
//                System.out.println("    " + "GPS Longitude: "
//                        + gpsLongitudeDegrees.toDisplayString() + " degrees, "
//                        + gpsLongitudeMinutes.toDisplayString() + " minutes, "
//                        + gpsLongitudeSeconds.toDisplayString() + " seconds "
//                        + gpsLongitudeRef);

            }

//            System.out.println();

            final List<ImageMetadataItem> items = jpegMetadata.getItems();
            for (int i = 0; i < items.size(); i++) {
                final ImageMetadataItem item;
                item = items.get(i);
//                System.out.println("    " + "item: " + item);
            }

//            System.out.println();
        } else {

            br.close();
            fr.close();

        }
    }

    private static void printTagValue(final JpegImageMetadata jpegMetadata,
            final TagInfo tagInfo, BufferedWriter br) {
        final TiffField field = jpegMetadata.findEXIFValueWithExactMatch(tagInfo);
        if (field == null) {
            System.out.println(tagInfo.name + ": " + "Not Found.");
        } else {
            System.out.println(tagInfo.name + ": "
                    + field.getValueDescription());
            try {
                br.write(tagInfo.name + ": " + field.getValueDescription() + "\n");
            } catch (IOException ex) {
                Logger.getLogger(MetadataExample.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
