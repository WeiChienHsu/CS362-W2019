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
            "h3t://",
            ""
    ));

    public List<String> failedUrlSchemes = new ArrayList<String>(Arrays.asList(
            "h3t://",
            "3hc://",
            "http:/",
            "http//",
            "//::/"
    ));

    public List<String> passedUrlAuthority = new ArrayList<String>(Arrays.asList(
            "www.google.com",
            "www.google.com.",
            "go.com",
            "go.au",
            "255.255.255.255",
            "0.0.0.0"
    ));

    public List<String> failedUrlAuthority = new ArrayList<String>(Arrays.asList(
            "1.2.3.4.5",
            ".1.2.3.4",
            "256.256.256.256",
            "go.a1a",
            "aaa.",
            "aaa"
    ));

    public List<String> passedUrlPort = new ArrayList<String>(Arrays.asList(
            ":80",
            ":65535",
            ":0",
            ""
    ));

    public List<String> failedUrlPort = new ArrayList<String>(Arrays.asList(
            ":-1",
            ":65536",
            ":999999999999999999",
            ":65a"
    ));


    public List<String> passedUrlPath = new ArrayList<String>(Arrays.asList(
            "/test1",
            "/t123",
            "/$23",
            "/test1/",
            "/test1/file",
            ""
    ));

    public List<String> failedUrlPath = new ArrayList<String>(Arrays.asList(
            "/..",
            "/../",
            "/..//file",
            "/test1//file"
    ));


    public int TEST_TIMES = 300;
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
    //You need to create more test cases for your Partitions if you need to

    public void testIsValid()
    {
        //You can use this function for programming based testing
        Random random = new Random(System.currentTimeMillis());
        int test_count = 0;

        while (test_count++ < TEST_TIMES)
        {

            int r = random.nextInt(NUM_PASSED_URL_SCHEME);

            List<String> schemes = new ArrayList();

            for (int index = 0; index <= r; index++)
            {
                schemes.add(getParts("passedUrlSchemes", index));
            }

            String[] schemeArray = new String[schemes.size()];
            schemeArray = schemes.toArray(schemeArray);

            passedUrlTest(schemeArray, schemes, random);
        }
    }

    public void passedUrlTest(String[] schemeArray, List<String> schemes, Random random)
    {
        UrlValidator urlValidator;
        urlValidator = new UrlValidator(schemeArray);
        /* Randomly test the valid and invalid status */
        boolean expectValidation = random.nextBoolean();

        String url;

        if (expectValidation)
        {
            String[] parts = generateValidUrl(schemes, expectValidation, random);

            if (!schemes.contains(parts[0]))
            {
                expectValidation = false;
            }

            StringBuilder sb = new StringBuilder();
            sb.append(parts[0]); sb.append(parts[1]); sb.append(parts[2]); sb.append(parts[3]);
            url = sb.toString();
        }
        else
        {
            url = generateInvalidUrl(random);
        }

        boolean actualValidation = urlValidator.isValid(url);

        if(expectValidation != actualValidation)
        {
            /* Print the error message */
            System.out.println("url: " + url + " should have been: " + expectValidation);
        }
    }

    public String[] generateValidUrl(List<String> schemes, boolean valid, Random random)
    {
        String scheme = getParts("passedUrlSchemes", random.nextInt(NUM_PASSED_URL_SCHEME));
        String auth = getParts("passedUrlAuthority", random.nextInt(NUM_PASSED_URL_AUTHORITY));
        String port = getParts("passedUrlPort", random.nextInt(NUM_PASSED_URL_PORT));
        String path = getParts("passedUrlPath", random.nextInt(NUM_PASSED_URL_PATH));
        return new String[]{scheme, auth, port, path};
    }

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

    public String generateUrlPart(Random random, List<String> passed, List<String> failed)
    {
        boolean isPassed = random.nextBoolean();
        int passed_index = random.nextInt(passed.size());
        int failed_index = random.nextInt(failed.size());
        return isPassed ? passed.get(passed_index) : failed.get(failed_index);
    }

    public String getParts(String targetScheme, int index)
    {
        switch (targetScheme)
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



    public static void main(String[] args) {

    }



}
