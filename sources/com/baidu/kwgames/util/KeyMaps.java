package com.baidu.kwgames.util;

import com.baidu.kwgames.Units;
import com.blankj.utilcode.util.StringUtils;
import java.io.ByteArrayInputStream;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
/* loaded from: classes.dex */
public class KeyMaps extends DefaultHandler {
    private static SModelItem s_modelItem;
    private String tagName = null;

    /* loaded from: classes.dex */
    public class SModelItem {
        public byte[][] arrKeyMapData;
        public String[] arrKeyMapName;
        public boolean boBendScreen;
        public boolean boNotchScreen;
        public int nKeyMapCount;
        public String str3cName;
        public String strFolder;
        public String strName;
        public int wHeight;
        public int wSize;
        public int wWidth;

        SModelItem() {
            this.arrKeyMapName = new String[8];
            this.arrKeyMapData = new byte[8];
            this.wWidth = 0;
            this.wHeight = 0;
            this.wSize = 0;
            this.boBendScreen = false;
            this.boNotchScreen = false;
            this.nKeyMapCount = 0;
        }

        SModelItem(String str, String str2, int i, int i2, int i3, boolean z, boolean z2, String str3) {
            this.arrKeyMapName = new String[8];
            this.arrKeyMapData = new byte[8];
            this.str3cName = str;
            this.strName = str2;
            this.wWidth = i;
            this.wHeight = i2;
            this.wSize = i3;
            this.boBendScreen = z;
            this.boNotchScreen = z2;
            this.strFolder = str3;
        }
    }

    public static SModelItem get_model_item() {
        return s_modelItem;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startDocument() throws SAXException {
        s_modelItem = null;
    }

    @Override // org.xml.sax.helpers.DefaultHandler, org.xml.sax.ContentHandler
    public void startElement(String str, String str2, String str3, Attributes attributes) throws SAXException {
        SModelItem sModelItem;
        if (str2.equals("ID")) {
            if (s_modelItem == null) {
                String value = attributes.getValue("c");
                if (value.equals(Units.getSystemModel())) {
                    String value2 = attributes.getValue("id");
                    int string_2_int = Units.string_2_int(attributes.getValue("w"));
                    int string_2_int2 = Units.string_2_int(attributes.getValue("h"));
                    int string_2_int3 = Units.string_2_int(attributes.getValue("sz"));
                    int string_2_int4 = Units.string_2_int(attributes.getValue("f"));
                    s_modelItem = new SModelItem(value, value2, string_2_int, string_2_int2, string_2_int3, (string_2_int4 & 1) != 0, (string_2_int4 & 2) != 0, attributes.getValue("d"));
                }
            }
        } else if (str2.equals("DIR") && (sModelItem = s_modelItem) != null && sModelItem.nKeyMapCount == 0 && attributes.getValue("n").equals(s_modelItem.strFolder)) {
            for (int i = 1; i <= 8; i++) {
                String value3 = attributes.getValue("P" + i);
                if (StringUtils.isEmpty(value3)) {
                    break;
                }
                int i2 = i - 1;
                s_modelItem.arrKeyMapName[i2] = value3.substring(2);
                s_modelItem.arrKeyMapData[i2] = Base64.getDecoder().decode(attributes.getValue("K" + i));
                s_modelItem.nKeyMapCount = i;
            }
        }
        this.tagName = str2;
    }

    public static void init() {
        byte[] read_assets = Util.read_assets("KeyMaps.bin");
        if (read_assets == null || read_assets.length <= 4) {
            return;
        }
        Inflater inflater = new Inflater();
        inflater.setInput(read_assets, 4, read_assets.length - 4);
        byte[] bArr = new byte[Units.BYTE2INT(read_assets[0], read_assets[1], read_assets[2], read_assets[3]) + 1024];
        try {
            inflater.inflate(bArr);
            SAXParser newSAXParser = SAXParserFactory.newInstance().newSAXParser();
            KeyMaps keyMaps = new KeyMaps();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
            newSAXParser.parse(byteArrayInputStream, keyMaps);
            byteArrayInputStream.close();
        } catch (DataFormatException e) {
            e.printStackTrace();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
        inflater.end();
    }
}
