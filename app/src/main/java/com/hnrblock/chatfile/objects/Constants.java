package com.hnrblock.chatfile.objects;

public class Constants {

//    public static String SERVER_URL = "https://test.hrblock.in/chatfile/";
    public static String SERVER_URL = "https://tax.hrblock.in/chatfile/";


    public static String RETURN_FILED= "return_filed";
    public static String TOKEN = "access_token";
    public static String Q_COUNT = "total_questions";
    public static String HCUID = "HardCodedUserID";
    public static String HCUID_VAL = "hrbandroidappuser";
    public static String HCPASS = "HardCodedPassword";
    public static String HCPASS_VAL = "#sdrtbcd1674#@fgotd@";
    public static String AUTH_TOKEN = SERVER_URL + "createToken";
    public static String API = SERVER_URL + "api/";
    // public static String QUESTIONS = API +
    // "Question/Get_Email_SummaryRecord";
    public static String GET_STATES = API + "Common/getStateList";
    public static String QUESTIONS = API + "Question/getQuestionMaster";
    public static String GET_SAVED_QUESTIONS = API
            + "Question/getSavedQuestions";
    public static String SAVE_QUESTIONS = API + "Question/saveQuestions";
    public static String CHECK_EMAIL = API + "signup/checkemail";
    public static String CHECK_LOGIN = API + "signup/validateUser";
    public static String CHANGE_PASSWORD = API + "Signup/forgotPassword";//POST -- HardCodedUserID, HardCodedPassword, email_id
    public static String CREATE_ACCOUNT = API + "signup/createaccount";
    public static String GET_COMPUTATION = API + "Common/getComputationdiy";//POST -- token ---- After last question (128)
    public static String E_FILE = API + "common/addToEfile";//POST -- token ----
    public static String GET_ITRV_STATUS = API + "Common/GetITRVStatus";//GET -- token
    public static String ADD_REFERREL = API + "Common/AddReferel";//POST -- Client_ID, Email, Name

    // Texts
    public static String TEXT_MALE = "Sir";
    public static String TEXT_FEMALE = "Madam";
    public static String LAST_OPENED = "last_opened";
}
