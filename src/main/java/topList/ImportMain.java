package topList;

import BO.DataFormatBO;
import com.ctrip.gs.destination.admin.soa.service.contract.CategoryPoiDto;
import com.ctrip.gs.destination.admin.soa.service.contract.ImportDataToCategoryTableRequestType;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import soaproxy.AdminSOAProxy;

import java.io.InputStream;
import java.util.*;

/**
 * Created by lilianga on 2018/6/8.
 */
public class ImportMain {
    public void processOneSheet(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        // To look up the Sheet Name / Sheet Order / rID,
        //  you need to process the core Workbook stream.
        // Normally it's of the form rId# or rSheet#
        InputStream sheet1 = r.getSheet("rId1");
        InputSource sheetSource = new InputSource(sheet1);
        parser.parse(sheetSource);
        sheet1.close();
    }

    public void processAllSheets(String filename) throws Exception {
        OPCPackage pkg = OPCPackage.open(filename);
        XSSFReader r = new XSSFReader(pkg);
        SharedStringsTable sst = r.getSharedStringsTable();

        XMLReader parser = fetchSheetParser(sst);

        Iterator<InputStream> sheets = r.getSheetsData();
        while (sheets.hasNext()) {
            System.out.println("Processing new sheet:\n");
            InputStream sheet = sheets.next();
            InputSource sheetSource = new InputSource(sheet);
            parser.parse(sheetSource);
            sheet.close();
            System.out.println("");
        }
    }

    public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("com.sun.org.apache.xerces.internal.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst);
        parser.setContentHandler(handler);
        return parser;
    }

    static List<DataFormatBO> resultList = new ArrayList<DataFormatBO>();

    /**
     * See org.xml.sax.helpers.DefaultHandler javadocs
     */
    private static class SheetHandler extends DefaultHandler {
        private SharedStringsTable sst;
        private String lastContents;
        private boolean nextIsString;
        private String locationName;
        private DataFormatBO data;

        private SheetHandler(SharedStringsTable sst) {
            this.sst = sst;
        }

        public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
            // c => cell
            if (name.equals("c")) {
                // Print the cell reference
                System.out.print(attributes.getValue("r") + " - ");
                locationName = attributes.getValue("r");
                // Figure out if the value is an index in the SST
                String cellType = attributes.getValue("t");
                if (cellType != null && cellType.equals("s")) {
                    nextIsString = true;
                } else {
                    nextIsString = false;
                }
            }
            // Clear contents cache
            lastContents = "";
        }

        public void endElement(String uri, String localName, String name) throws SAXException {
            // Process the last contents as required.
            // Do now, as characters() may be called more than once
            if (nextIsString) {
                int idx = Integer.parseInt(lastContents);
                lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                nextIsString = false;
            }

            // v => contents of a cell  &&!(locationName.length()==2&&locationName.endsWith("1"))
            // Output after we've seen the string contents
            if (name.equals("v") && !(locationName.length() == 2 && locationName.endsWith("1"))) {
                if (locationName.startsWith("A")) {
                    data = new DataFormatBO();
                    data.setDistrictId(Long.parseLong(lastContents));
                } else if (locationName.startsWith("B")) {
                    data.setCategoryCode(lastContents);
                } else if (locationName.startsWith("C")) {
                    data.setCategoryName(lastContents);
                } else if (locationName.startsWith("D")) {
                    data.setBrief(lastContents);
                } else if (locationName.startsWith("E")) {
                    data.setIntroduction(lastContents);
                } else if (locationName.startsWith("F")) {
                    data.setImageUrl(lastContents);
                } else if (locationName.startsWith("G")) {
                    data.setModifiedRank(Integer.parseInt(lastContents));
                } else if (locationName.startsWith("K")) {
                    //data.setModifiedRank(Integer.parseInt(lastContents));
                } else if (locationName.startsWith("H")) {
                    data.setPoiId(Long.parseLong(lastContents));
                } else if (locationName.startsWith("I")) {
                    data.setPoiRank(Integer.parseInt(lastContents));
                    resultList.add(data);
                }
                System.out.println(lastContents);
            }
        }

        public void characters(char[] ch, int start, int length) throws SAXException {
            lastContents += new String(ch, start, length);
        }
    }

    public static void main(String[] args) throws Exception {
        ImportMain example = new ImportMain();
        System.out.println("11");
        example.processOneSheet("D:\\importData\\机器打榜数据（第五批).xlsx");
        //example.processAllSheets("D:\\testData.xlsx");

        System.out.println(ImportMain.resultList.size());
        final Map<String, ImportDataToCategoryTableRequestType> categoryMap = new HashMap<>();
        for (DataFormatBO bo : resultList) {
            if (categoryMap.containsKey(bo.getDistrictId() + bo.getCategoryCode())) {
                CategoryPoiDto poi = new CategoryPoiDto();
                poi.setPoiId(bo.getPoiId());
                poi.setRank((long) bo.getPoiRank());
                List<CategoryPoiDto> poiDtoList = categoryMap.get(bo.getDistrictId() + bo.getCategoryCode()).getCategoryPoiList();
                poiDtoList.add(poi);
            } else {
                ImportDataToCategoryTableRequestType category = new ImportDataToCategoryTableRequestType();
                category.setCategoryName(bo.getCategoryName());
                category.setCategoryCode(bo.getCategoryCode());
                category.setBrief(bo.getBrief());
                category.setCategoryId(bo.getCategoryId());
                category.setDistrictId(bo.getDistrictId());
                category.setImageId(bo.getImageUrl());
                category.setModifiedRank((long) bo.getModifiedRank());
                category.setIntroduction(bo.getIntroduction());
                category.setSourceFrom("AI");
                if ("mustPlay".equals(bo.getCategoryCode())){
                    category.setChannelType(113);
                    category.setCategoryTags("19996");
                }else{
                    category.setChannelType(3);
                }
                CategoryPoiDto poi = new CategoryPoiDto();
                poi.setPoiId(bo.getPoiId());
                poi.setRank((long) bo.getPoiRank());
                List<CategoryPoiDto> list = new ArrayList<>();
                list.add(poi);
                category.setCategoryPoiList(list);
                categoryMap.put(bo.getDistrictId() + bo.getCategoryCode(), category);
            }
        }

        for (String k : categoryMap.keySet()) {
            AdminSOAProxy.getInstance().importDataToCategoryTable(categoryMap.get(k));
        }
        System.out.println("over!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }
}
