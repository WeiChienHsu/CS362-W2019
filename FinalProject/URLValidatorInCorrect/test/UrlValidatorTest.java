import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class UrlValidatorTest extends TestCase {

    /* List all passed and failed test cases */
    public List<String> passedUrlSchemes = new ArrayList<String>(Arrays.asList(
            "http://",
            "ftp://",
            "h3t://"
    ));

    public List<String> failedUrlSchemes = new ArrayList<String>(Arrays.asList(
            "http:/",
            "http:",
            "://"
    ));

    public List<String> passedUrlAuthority = new ArrayList<String>(Arrays.asList(
            "www.google.com",
            "go.com",
            "go.au",
            "255.255.255.255",
            "0.0.0.0"
    ));

    public List<String> failedUrlAuthority = new ArrayList<String>(Arrays.asList(
            "1.2.3.4.5",
            "1.2.3",
            "256.256.256.256",
            "go.a1a",
            "aaa"
    ));

    public List<String> passedUrlPort = new ArrayList<String>(Arrays.asList(
            ":65535",
            ":0"
    ));

    public List<String> failedUrlPort = new ArrayList<String>(Arrays.asList(
            ":-1",
            ":65536",
            ":65a"
    ));

    public List<String> passedUrlPath = new ArrayList<String>(Arrays.asList(
            "/t123",
            "/$23",
            "/test1/",
            "/test1/file"
    ));

    public List<String> failedUrlPath = new ArrayList<String>(Arrays.asList(
            "/..",
            "/../",
            "/..//file",
            "/test1//file"
    ));


    public int NUM_PASSED_URL_SCHEME = passedUrlSchemes.size();
    public int NUM_FAILED_URL_SCHEME = failedUrlSchemes.size();
    public int NUM_PASSED_URL_AUTHORITY = passedUrlAuthority.size();
    public int NUM_FAILED_URL_AUTHORITY = failedUrlAuthority.size();
    public int NUM_PASSED_URL_PORT = passedUrlPort.size();
    public int NUM_FAILED_URL_PORT = failedUrlPort.size();
    public int NUM_PASSED_URL_PATH = passedUrlPath.size();
    public int NUM_FAILED_URL_PATH = failedUrlPath.size();

    public UrlValidatorTest(String testName) {
        super(testName);
    }

    public void testManualTest()
    {
        //You can use this function to implement your manual testing

    }

    public void testYourFirstPartition()
    {
        //You can use this function to implement your First Partition testing

    }

    public void testYourSecondPartition()
    {
        //You can use this function to implement your Second Partition testing

    }

    /* Referenced from the Correct Project */
    @Override
    protected void setUp() {
        for (int index = 0; index < testPartsIndex.length - 1; index++) {
            testPartsIndex[index] = 0;
        }
    }

    /* Referenced from the Correct Project */
    public void testIsValid() {
        testIsValid(testUrlParts, UrlValidator.ALLOW_ALL_SCHEMES);
        setUp();
    }

    /* Referenced from the Correct Project */
    public void testIsValid(Object[] testObjects, long allowAllSchemes) {
        UrlValidator urlValidator = new UrlValidator(null, null, allowAllSchemes);
        Random random = new Random(System.currentTimeMillis());
        do {
            passedUrlTest(urlValidator, random);
        } while (incrementTestPartsIndex(testPartsIndex, testObjects));
    }

    /* Create by Wei-Chien Hsu */
    public void passedUrlTest(UrlValidator urlValidator, Random random)
    {
        boolean expectValidation = random.nextBoolean();

        String url;

        if (expectValidation)
        {
            String[] parts = generateValidUrl(expectValidation, random);
            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]); sb.append(parts[1]); sb.append(parts[2]); sb.append(parts[3]);
            url = sb.toString();
        }
        else
        {
            url = generateInvalidUrl(random);
        }

        boolean actualValidation = urlValidator.isValid(url);


        boolean result = urlValidator.isValid(url);

        if(expectValidation != actualValidation)
        {
            /* Print the error message */
            System.out.println("url: " + url + " should have been: " + expectValidation);
        }
    }

    /* Create by Wei-Chien Hsu */
    public String[] generateValidUrl(boolean valid, Random random)
    {
        String scheme = getParts("passedUrlSchemes", random.nextInt(NUM_PASSED_URL_SCHEME));
        String auth = getParts("passedUrlAuthority", random.nextInt(NUM_PASSED_URL_AUTHORITY));
        String port = getParts("passedUrlPort", random.nextInt(NUM_PASSED_URL_PORT));
        String path = getParts("passedUrlPath", random.nextInt(NUM_PASSED_URL_PATH));
        return new String[]{scheme, auth, port, path};
    }

    /* Create by Wei-Chien Hsu */
    /* Combine either valid parts or invalid parts */
    public String generateInvalidUrl(Random random)
    {
        boolean valid = true;
        String scheme = "";
        String port = "";
        String auth = "";
        String path = "";
        StringBuilder url = new StringBuilder();

        while(valid)
        {
            scheme = generateUrlPart(random, passedUrlSchemes, failedUrlSchemes);
            if(failedUrlSchemes.contains(scheme)) valid = false;

            auth = generateUrlPart(random, passedUrlAuthority, failedUrlAuthority);
            if(failedUrlAuthority.contains(auth)) valid = false;

            port = generateUrlPart(random, passedUrlPort, failedUrlPort);
            if(failedUrlPort.contains(port)) valid = false;

            path = generateUrlPart(random, passedUrlPath, failedUrlPath);
            if(failedUrlPath.contains(path)) valid = false;
        }

        url.append(scheme); url.append(auth); url.append(port); url.append(path);
        return url.toString();
    }

    /* Create by Wei-Chien Hsu */
    public String generateUrlPart(Random random, List<String> passed, List<String> failed)
    {
        boolean isPassed = random.nextBoolean();
        int passed_index = random.nextInt(passed.size());
        int failed_index = random.nextInt(failed.size());
        return isPassed ? passed.get(passed_index) : failed.get(failed_index);
    }

    /* Create by Wei-Chien Hsu */
    public String getParts(String targetPart, int index)
    {
        switch (targetPart)
        {
            case "passedUrlSchemes":
                return passedUrlSchemes.get(index);

            case "failedUrlSchemes":
                return failedUrlSchemes.get(index);

            case "passedUrlAuthority":
                return passedUrlAuthority.get(index);

            case "failedUrlAuthority":
                return failedUrlAuthority.get(index);

            case "passedUrlPort":
                return passedUrlPort.get(index);

            case "failedUrlPort":
                return failedUrlPort.get(index);

            case "passedUrlPath":
                return passedUrlPath.get(index);

            case "failedUrlPath":
                return failedUrlPath.get(index);
        }
        System.out.println("error");
        return "";
    }

    /* Referenced from the Correct Project */
    static boolean incrementTestPartsIndex(int[] testPartsIndex, Object[] testParts) {
        boolean carry = true;  //add 1 to lowest order part.
        boolean maxIndex = true;
        for (int testPartsIndexIndex = testPartsIndex.length - 1; testPartsIndexIndex >= 0; --testPartsIndexIndex) {
            int index = testPartsIndex[testPartsIndexIndex];
            ResultPair[] part = (ResultPair[]) testParts[testPartsIndexIndex];
            if (carry) {
                if (index < part.length - 1) {
                    index++;
                    testPartsIndex[testPartsIndexIndex] = index;
                    carry = false;
                } else {
                    testPartsIndex[testPartsIndexIndex] = 0;
                    carry = true;
                }
            }
            maxIndex &= (index == (part.length - 1));
        }
        return (!maxIndex);
    }

    ResultPair[] testUrlScheme = {new ResultPair("http://", true),
            new ResultPair("ftp://", true),
            new ResultPair("h3t://", true),
            new ResultPair("3ht://", false),
            new ResultPair("http:/", false),
            new ResultPair("http:", false),
            new ResultPair("http/", false),
            new ResultPair("://", false),
            new ResultPair("", true)};

    ResultPair[] testUrlAuthority = {new ResultPair("www.google.com", true),
            new ResultPair("go.com", true),
            new ResultPair("go.au", true),
            new ResultPair("0.0.0.0", true),
            new ResultPair("255.255.255.255", true),
            new ResultPair("256.256.256.256", false),
            new ResultPair("255.com", true),
            new ResultPair("1.2.3.4.5", false),
            new ResultPair("1.2.3.4.", false),
            new ResultPair("1.2.3", false),
            new ResultPair(".1.2.3.4", false),
            new ResultPair("go.a", false),
            new ResultPair("go.a1a", false),
            new ResultPair("go.1aa", false),
            new ResultPair("aaa.", false),
            new ResultPair(".aaa", false),
            new ResultPair("aaa", false),
            new ResultPair("", false)
    };
    ResultPair[] testUrlPort = {new ResultPair(":80", true),
            new ResultPair(":65535", true),
            new ResultPair(":0", true),
            new ResultPair("", true),
            new ResultPair(":-1", false),
            new ResultPair(":65636",false),
            new ResultPair(":65a", false)
    };
    ResultPair[] testPath = {new ResultPair("/test1", true),
            new ResultPair("/t123", true),
            new ResultPair("/$23", true),
            new ResultPair("/..", false),
            new ResultPair("/../", false),
            new ResultPair("/test1/", true),
            new ResultPair("", true),
            new ResultPair("/test1/file", true),
            new ResultPair("/..//file", false),
            new ResultPair("/test1//file", false)
    };
    //Test allow2slash, noFragment
    ResultPair[] testUrlPathOptions = {new ResultPair("/test1", true),
            new ResultPair("/t123", true),
            new ResultPair("/$23", true),
            new ResultPair("/..", false),
            new ResultPair("/../", false),
            new ResultPair("/test1/", true),
            new ResultPair("/#", false),
            new ResultPair("", true),
            new ResultPair("/test1/file", true),
            new ResultPair("/t123/file", true),
            new ResultPair("/$23/file", true),
            new ResultPair("/../file", false),
            new ResultPair("/..//file", false),
            new ResultPair("/test1//file", true),
            new ResultPair("/#/file", false)
    };

    ResultPair[] testUrlQuery = {new ResultPair("?action=view", true),
            new ResultPair("?action=edit&mode=up", true),
            new ResultPair("", true)
    };

    Object[] testUrlParts = {testUrlScheme, testUrlAuthority, testUrlPort, testPath, testUrlQuery};
    Object[] testUrlPartsOptions = {testUrlScheme, testUrlAuthority, testUrlPort, testUrlPathOptions, testUrlQuery};
    int[] testPartsIndex = {0, 0, 0, 0, 0};

    //---------------- Test data for individual url parts ----------------
    ResultPair[] testScheme = {new ResultPair("http", true),
            new ResultPair("ftp", false),
            new ResultPair("httpd", false),
            new ResultPair("telnet", false)};

    public static void main(String[] args) {
        UrlValidator urlValidator;
        urlValidator = new UrlValidator();
        System.out.println(urlValidator.isValid("http://www.google.com"));
    }



}
