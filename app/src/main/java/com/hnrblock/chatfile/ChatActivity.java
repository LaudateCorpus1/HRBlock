package com.hnrblock.chatfile;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.hnrblock.chatfile.ButtonsGridViewAdapter.OptionClickListener;
import com.hnrblock.chatfile.ChatArrayAdapter.MessageEditClickListener;
import com.hnrblock.chatfile.helpers.PopupUtils;
import com.hnrblock.chatfile.helpers.PostManager;
import com.hnrblock.chatfile.helpers.PrefsManager;
import com.hnrblock.chatfile.objects.AnswerInputTypes;
import com.hnrblock.chatfile.objects.ChatMessage;
import com.hnrblock.chatfile.objects.Constants;
import com.hnrblock.chatfile.objects.NoSpaceInputFilter;
import com.hnrblock.chatfile.objects.Question;
import com.hnrblock.chatfile.objects.ReportRow;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ChatActivity extends Activity implements MessageEditClickListener,
        OptionClickListener, OnScrollListener {
    public static ArrayList<Integer> NO_EDIT = new ArrayList<Integer>() {
        {
            add(2);//2,7,13,18,19,28,41,42,52,54,62,97,99,125,126,129
            add(7);
            add(13);
            add(18);
            add(19);
            add(41);
            add(42);
            add(46);
            add(52);
            add(54);
            add(55);
            add(62);
            add(97);
            add(124);
            add(125);
            add(126);
            add(129);
        }
    };
    public int TOTAL_QUESTIONS = 0;
    public static final int QNO_1 = 1;
    public static final int QNO_5 = 5;
    public static final int QNO_13 = 13;
    public static final int QNO_PAN = 31;
    public static final int QNO_TAN = 48;
    public static final int QNO_EMAIL_ID = 8;
    public static final int QNO_OLD_PASS = 9;
    public static final int QNO_NEW_PASS = 10;
    public static int QNO_SKIP_CONGRATS = 11;
    public static int QNO_DOB = 32;
    public static int QNO_AADHAR = 37;
    public static int QNO_TAX_DEDUCTED = 50;
    public static int QNO_OTHER_INCOME = 51;
    public static int QNO_HOUSES = 57;
    public static int QNO_SKIP_HOUSES = 61;
    public static int QNO_CLAIM_HRA = 66;
    public static int QNO_SKIP_CLAIM_HRA = 69;
    public static int QNO_HRA_LTA_NOT = 69;// erase 67,68
    public static int QNO_DONATIONS = 70;// NO? goto 88
    public static int QNO_DONATIONS_MORE = 80;// NO? goto 88... also same like
    // bank
    public static int QNO_SKIP_DONATIONS = 89;
    public static int QNO_OTHER_DEDUCTIONS = 90;// NO? goto 94
    public static int QNO_SKIP_OTHER_DEDUCTIONS = 95;
    public static int QNO_BANK_1 = 106;// NO? goto 122
    public static int QNO_BANK_2 = 112;// NO? goto 122
    public static int QNO_BANK_3 = 118;// NO? goto 122
    public static int QNO_BANK_END = 124;

    // Sections
    public static int SECTION_START_1 = 3;//Profile
    public static int SECTION_START_2 = 20;//Address
    public static int SECTION_START_3 = 30;//More Info(Additional)
    public static int SECTION_START_4 = 43;//Income
    public static int SECTION_START_5 = 63;//Deductions
    public static int SECTION_START_6 = 100;//Bank Details
    public static int SECTION_START_7 = 127;//E-File
    public static int SECTION_END_1 = 18;
    public static int SECTION_END_2 = 28;
    public static int SECTION_END_3 = 41;
    public static int SECTION_END_4 = 54;
    public static int SECTION_END_5 = 61;
    public static int SECTION_END_6 = 97;
    public static int SECTION_END_7 = 99;
    public static int SECTION_END_8 = 125;
    public static int SECTION_END_9 = 129;
    //    public static int QNO_LAST = 128;
    public static int QNO_PLEASE_WAIT = 130;
    public static int QNO_LAST = 140;

    private ProgressDialog pDialog;

    private LinearLayout chatInputLayout, dateLayout/* , buttonsLayout */;
    private GridView buttonsLayout;
    private Spinner dropDown, yearSpinner, monthSpinner, dateSpinner;
    private Button sendButton, dateSendButton;
    private EditText chatInput;
    private int currentQ = 0, previousQ = -1, insertPos = -1,
            mLastFirstVisibleItem;
    private boolean loadNext = true, showingReport = false, validating = false,
            mIsScrollingUp = false, isForgotPass = false, isCheckingTextChange = false, showEFileButton = true, fileReturn = false/* , isActivatingTab = false */;
    public static String currentQId = "";
    // private WholeQuestion wholeQuestion;
    private Question question;
    // private ArrayList<Restriction> restrictions;

    private ListView chatView;
    private ChatArrayAdapter chatAdapter;

    private DatabaseHelper helper = new DatabaseHelper(this);
    private PopupMenu _menuPopup;
    private Button _menuMore;
    private TextView errorText, progressText;
    private RelativeLayout mainHelp;

    private RadioGroup tabGroup;
    private HorizontalScrollView tabScroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        PrefsManager.savePrefs(this, Constants.LAST_OPENED, System.currentTimeMillis());

        TOTAL_QUESTIONS = PrefsManager.getPrefs(this, Constants.Q_COUNT, 0);

        chatInputLayout = (LinearLayout) findViewById(R.id.chat_input_layout);
        dateLayout = (LinearLayout) findViewById(R.id.dateLayout);
        buttonsLayout = (GridView) findViewById(R.id.buttons_layout);
        dropDown = (Spinner) findViewById(R.id.drop_down);
        yearSpinner = (Spinner) findViewById(R.id.yearSpinner);
        monthSpinner = (Spinner) findViewById(R.id.monthSpinner);
        dateSpinner = (Spinner) findViewById(R.id.dateSpinner);
        setSpinnerPopupHeights();
        errorText = (TextView) findViewById(R.id.error_txt);
        progressText = (TextView) findViewById(R.id.progressText);
        mainHelp = (RelativeLayout) findViewById(R.id.mainHelp);
        mainHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                showToast("Popup here");
                PopupUtils.createPopup(ChatActivity.this, "Help", getString(R.string.help_text));
            }
        });
        _menuMore = (Button) findViewById(R.id.menu_options);
        _menuMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                showMenuPopup();
            }
        });

        chatView = (ListView) findViewById(R.id.chatView);
        chatView.setOnScrollListener(this);
        chatAdapter = new ChatArrayAdapter(this, R.layout.question, this);
        chatView.setAdapter(chatAdapter);

        tabGroup = (RadioGroup) findViewById(R.id.tabGroup);
        tabScroll = (HorizontalScrollView) findViewById(R.id.tabScroll);
        tabGroup.setOnCheckedChangeListener(checkChanged);

        setMenuPopup();

        sendButton = (Button) findViewById(R.id.sendButton);
        sendButton.setOnClickListener(onClickListener);
        dateSendButton = (Button) findViewById(R.id.dateSendButton);
        dateSendButton.setOnClickListener(onClickListener);

        chatInput = (EditText) findViewById(R.id.chatInput);
        // chatInput.setOnKeyListener(onKeyListener);
        chatInput.setOnEditorActionListener(actionListener);
        // if (helper.isLoggedIn()) {
        // ContentValues cv = new ContentValues();
        // cv.put("email_id", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
        // String pass = helper.getQuestion(QNO_OLD_PASS).getAnswer();
        // if (pass.equalsIgnoreCase("")) {
        // pass = helper.getQuestion(QNO_NEW_PASS).getAnswer();
        // }
        // cv.put("password", pass);
        // new GetToken().execute(cv);
        // } else {
        // checkLoadQuestion();
        // }
        checkLoadQuestion();
    }

    private void checkLoadQuestion() {
        chatInput.setText("");
        int q_no = currentQ + 1;
        Question q = helper.getQuestion(q_no);
        if (/*q_no <= QNO_PLEASE_WAIT && */q != null) {
            if (q.getQType() != 0 && !isNextAnswered(q_no)) {
                // load typing and question after delay
                sayTyping();
            } else {
                loadQuestion1();
            }
        } else {
            hideInputs();
            helper.checkUploadAnswers(1);
        }
    }

    private void sayTyping() {
        ChatMessage cm = new ChatMessage(true, "Typing...", "-1", "-1", "",
                false);
        chatAdapter.insert(cm, insertPos);
        hideInputs();
        loadAfterDelay();
        scrollMyListViewToBottom(-1);
    }

    private OnCheckedChangeListener checkChanged = new OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            // showToast("checked: " + checkedId);
            // if (!isActivatingTab) {
            if (chatAdapter.typingPos == -1) {
                if (checkedId == R.id.tab1) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_1));

                } else if (checkedId == R.id.tab2) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_2));

                } else if (checkedId == R.id.tab3) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_3));

                } else if (checkedId == R.id.tab4) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_4));

                } else if (checkedId == R.id.tab5) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_5));

                } else if (checkedId == R.id.tab6) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_6));
                } else if (checkedId == R.id.tab7) {
                    scrollMyListViewToBottom(chatAdapter
                            .getQPosition(SECTION_START_7));
                } else {
                }
                /*if (checkedId == R.id.tab1){
                    scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_1));
				} else if (checkedId == R.id.tab2){
					scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_2));
				} else if (checkedId == R.id.tab3){
					scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_3));
				} else if (checkedId == R.id.tab4){
					scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_4));
				} else if (checkedId == R.id.tab5){
					scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_5));
				} else if (checkedId == R.id.tab6){
					scrollMyListViewToBottom(chatAdapter
							.getQPosition(SECTION_START_6));
				}*/

            }
            // } else {
            // isActivatingTab = false;
            // }
        }
    };

    private void activateTab(int position) {
        //TODO scroll not working as expected
//        position = Integer.parseInt(chatAdapter.getItem(position).id);
        // isActivatingTab = true;
        tabGroup.setOnCheckedChangeListener(null);
        if (!mIsScrollingUp) {
            if (position < SECTION_START_2) {
                tabGroup.check(R.id.tab1);
                tabScrollLeft();
            } else if (position >= SECTION_START_2 && position < SECTION_START_3) {
                tabGroup.check(R.id.tab2);
                tabScrollLeft();
            } else if (position >= SECTION_START_3 && position < SECTION_START_4) {
                tabGroup.check(R.id.tab3);
                tabScrollLeft();
            } else if (position >= SECTION_START_4 && position < SECTION_START_5) {
                tabGroup.check(R.id.tab4);
                tabScrollRight();
            } else if (position >= SECTION_START_5 && position < SECTION_START_6) {
                tabGroup.check(R.id.tab5);
                tabScrollRight();
            } else if (position >= SECTION_START_6 && position < SECTION_START_7) {
                tabGroup.check(R.id.tab6);
                tabScrollRight();
            } else if (position >= SECTION_START_7) {
                tabGroup.check(R.id.tab7);
                tabScrollRight();
            }
        } else {
            if (position <= SECTION_END_1) {
                tabGroup.check(R.id.tab1);
                tabScrollLeft();
            } else if (position > SECTION_END_1 && position <= SECTION_END_2) {
                tabGroup.check(R.id.tab2);
                tabScrollLeft();
            } else if (position > SECTION_END_2 && position <= SECTION_END_3) {
                tabGroup.check(R.id.tab3);
                tabScrollLeft();
            } else if (position > SECTION_END_3 && position <= SECTION_END_4) {
                tabGroup.check(R.id.tab4);
                tabScrollRight();
            } else if (position > SECTION_END_4 && position <= SECTION_END_5) {
                tabGroup.check(R.id.tab5);
                tabScrollRight();
            } else if (position > SECTION_END_5) {
                tabGroup.check(R.id.tab6);
                tabScrollRight();
            }
        }
        tabGroup.setOnCheckedChangeListener(checkChanged);
    }

    private void setTabs() {
//        findViewById(R.id.tab2).setVisibility(View.GONE);
//        findViewById(R.id.tab3).setVisibility(View.GONE);
//        findViewById(R.id.tab4).setVisibility(View.GONE);
//        findViewById(R.id.tab5).setVisibility(View.GONE);
//        findViewById(R.id.tab6).setVisibility(View.GONE);
//        findViewById(R.id.tab7).setVisibility(View.GONE);
        if (currentQ <= SECTION_START_2) {
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab1)
                tabGroup.check(R.id.tab1);
        } else if (currentQ >= SECTION_START_2 && currentQ < SECTION_START_3) {
            findViewById(R.id.tab2).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab2) {
                tabScrollRight();
                tabGroup.check(R.id.tab2);
            }
        } else if (currentQ >= SECTION_START_3 && currentQ < SECTION_START_4) {
            findViewById(R.id.tab3).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab3) {
                tabScrollRight();
                tabGroup.check(R.id.tab3);
            }
        } else if (currentQ >= SECTION_START_4 && currentQ < SECTION_START_5) {
            findViewById(R.id.tab4).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab4) {
                tabScrollRight();
                tabGroup.check(R.id.tab4);
            }
        } else if (currentQ >= SECTION_START_5 && currentQ < SECTION_START_6) {
            findViewById(R.id.tab5).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab5) {
                tabScrollRight();
                tabGroup.check(R.id.tab5);
            }
        } else if (currentQ >= SECTION_START_6 && currentQ < SECTION_START_7) {
            findViewById(R.id.tab6).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab6) {
                tabScrollRight();
                tabGroup.check(R.id.tab6);
            }
        } else if (currentQ >= SECTION_START_7) {
            findViewById(R.id.tab7).setVisibility(View.VISIBLE);
            if (tabGroup.getCheckedRadioButtonId() != R.id.tab7) {
                tabScrollRight();
                tabGroup.check(R.id.tab7);
            }
        }
    }

    private void tabScrollRight() {
        tabScroll.postDelayed(new Runnable() {
            public void run() {
                tabScroll.fullScroll(HorizontalScrollView.FOCUS_RIGHT);
            }
        }, 100L);
    }

    private void tabScrollLeft() {
        tabScroll.postDelayed(new Runnable() {
            public void run() {
                tabScroll.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        }, 100L);
    }

    private int getDOBDiff() {
        int toReturn = 0;
        Date today = new Date(System.currentTimeMillis());
        Calendar todayCal = Calendar.getInstance();
        todayCal.setTime(today);
        Date date = new Date();
        Calendar bDayCal = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yyyy");
        try {
            date = format.parse(helper.getQuestion(QNO_DOB).getAnswer());
            bDayCal.setTime(date);
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int todayM = todayCal.get(Calendar.MONTH);
        int bDayM = bDayCal.get(Calendar.MONTH);
        if (todayCal.get(Calendar.MONTH) == bDayCal.get(Calendar.MONTH)) {
            if (todayCal.get(Calendar.DAY_OF_MONTH) > bDayCal
                    .get(Calendar.DAY_OF_MONTH)) {
                toReturn = 1;
            } else if (todayCal.get(Calendar.DAY_OF_MONTH) < bDayCal
                    .get(Calendar.DAY_OF_MONTH)) {
                toReturn = -1;
            }
        }
        return toReturn;
    }

    private void loadQuestion1() {
        currentQ++;
        setTabs();
//        if (currentQ == QNO_PLEASE_WAIT + 1) {
//            currentQ = QNO_LAST + 1;
//        }
        //email n pass
        if (helper.isLoggedIn() && currentQ >= QNO_OLD_PASS
                && currentQ < QNO_SKIP_CONGRATS + 1) {
            currentQ = QNO_SKIP_CONGRATS + 1;
        }
        if (currentQ == SECTION_END_7 - 1) {//Skip 1st computation
            currentQ = SECTION_END_7 + 1;
        }
        if (currentQ == QNO_CLAIM_HRA + 3) {//Skip 1st computation
            currentQ = QNO_CLAIM_HRA + 4;
        }
        if (currentQ > QNO_DOB && currentQ < QNO_DOB + 4) {// next to DOB
            // question
            if (currentQ == QNO_DOB + 1) {// still to calculate
                int dobDiff = getDOBDiff();
                if (dobDiff > 0) {
                    currentQ = QNO_DOB + 3;// b'day passed
                } else if (dobDiff < 0) {
                    currentQ = QNO_DOB + 2;// b'day coming
                }
            } else {
                currentQ = QNO_DOB + 4;// all done with b'days
            }
        }
        if (currentQ == QNO_OTHER_INCOME + 1) {
            if (helper.getQuestion(QNO_OTHER_INCOME).getAnswer()
                    .equalsIgnoreCase("no")) {
                currentQ = QNO_OTHER_INCOME + 2;
            }
        }
        if (currentQ == QNO_OTHER_INCOME + 2) {
            if (helper.getQuestion(QNO_OTHER_INCOME).getAnswer()
                    .equalsIgnoreCase("yes")) {
                currentQ = QNO_LAST + 1;
            }
        }

        if (currentQ >= QNO_HOUSES + 1
                && currentQ <= QNO_SKIP_HOUSES
                && helper.getQuestion(QNO_HOUSES).getAnswer()
                .equalsIgnoreCase("zero")) {
            currentQ = QNO_SKIP_HOUSES + 1;// skip house details
        } else if (currentQ == QNO_SKIP_HOUSES) {
            currentQ = QNO_SKIP_HOUSES + 1;// skip this question
        } else if (currentQ >= QNO_CLAIM_HRA + 1
                && currentQ <= QNO_SKIP_CLAIM_HRA
                && helper.getQuestion(QNO_CLAIM_HRA).getAnswer()
                .equalsIgnoreCase("no")) {
            currentQ = QNO_SKIP_CLAIM_HRA + 1;// skip HRA
        } else if (currentQ >= QNO_DONATIONS + 1
                && currentQ <= QNO_SKIP_DONATIONS
                && helper.getQuestion(QNO_DONATIONS).getAnswer()
                .equalsIgnoreCase("no")) {
            removeAnswers(QNO_DONATIONS + 1, QNO_SKIP_DONATIONS);
            // checkLoadQuestion();
            currentQ = QNO_SKIP_DONATIONS + 1;// skip donations
        } else if (currentQ >= QNO_DONATIONS_MORE + 1
                && currentQ <= QNO_SKIP_DONATIONS
                && helper.getQuestion(QNO_DONATIONS_MORE).getAnswer()
                .equalsIgnoreCase("no")) {
            removeAnswers(QNO_DONATIONS_MORE + 1, QNO_SKIP_DONATIONS);
            // checkLoadQuestion();
            currentQ = QNO_SKIP_DONATIONS + 1;// skip donations
        } else if (currentQ >= QNO_OTHER_DEDUCTIONS + 1
                && currentQ <= QNO_SKIP_OTHER_DEDUCTIONS
                && helper.getQuestion(QNO_OTHER_DEDUCTIONS).getAnswer()
                .equalsIgnoreCase("no")) {
            currentQ = QNO_SKIP_OTHER_DEDUCTIONS + 1;// skip other deductions
        } else if (currentQ >= QNO_BANK_1 + 1
                && currentQ <= QNO_BANK_END
                && helper.getQuestion(QNO_BANK_1).getAnswer()
                .equalsIgnoreCase("no")) {
            currentQ = QNO_BANK_END;// skip other deductions
        } else if (currentQ >= QNO_BANK_2 + 1
                && currentQ <= QNO_BANK_END
                && helper.getQuestion(QNO_BANK_2).getAnswer()
                .equalsIgnoreCase("no")) {
            currentQ = QNO_BANK_END;// skip other deductions
        } else if (currentQ >= QNO_BANK_3 + 1
                && currentQ <= QNO_BANK_END
                && helper.getQuestion(QNO_BANK_3).getAnswer()
                .equalsIgnoreCase("no")) {
            currentQ = QNO_BANK_END;// skip other deductions
        }
        if (currentQ == SECTION_END_1 || currentQ == SECTION_END_2
                || currentQ == SECTION_END_3 || currentQ == SECTION_END_4
                || currentQ == SECTION_END_5 || currentQ == SECTION_END_6
                || currentQ == SECTION_END_7 || currentQ == SECTION_END_8 || currentQ == SECTION_END_9) {
            helper.checkUploadAnswers(1);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    helper.checkUploadAnswers(1);
                    try {
                        if (currentQ == SECTION_END_7 || currentQ == SECTION_END_9) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        new GetComputation().execute();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 2000);
                        } else {
                            showSummary();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, 2000);
        } else {
            question = helper.getQuestion(currentQ);
            if (question != null && !question.getTitle().equalsIgnoreCase("")) {
                currentQId = question.getId();
                String qTitle = question.getTitle();
                if (currentQ == QNO_5 || currentQ == QNO_13) {
                    String longAnswer = getLongAnswer(
                            helper.getQuestion(QNO_5 - 1).getAnswer(),
                            (QNO_5 - 1) + "");
                    qTitle = qTitle.replace("$Gender", longAnswer);
                } else if (currentQ == QNO_1) {
                    qTitle = getGreeting();
                }
                ChatMessage cm = new ChatMessage(true, qTitle,
                        question.getId(), question.getTimestamp(),
                        question.getHelp2(), question.getMinLength() == 0);
                sendChat(cm);
                if (question.getQType() == 0 || currentQ == QNO_LAST) {
                    // if (isNextAnswered(currentQ)) {
                    // loadQuestion1();
                    // } else {
                    // // delay and load next
                    // loadAfterDelay();
                    // }
                    checkLoadQuestion();
                } else {
                    if (question.getAnswer() != null
                            && question.getIsValid() == 1) {
                        if (question.getId()
                                .equalsIgnoreCase(QNO_EMAIL_ID + "")
                                && !helper.isLoggedIn()) {
                            ContentValues cv = new ContentValues();
                            cv.put("email_id", question.getAnswer());
                            cv.put(Constants.HCUID, Constants.HCUID_VAL);
                            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
                            new CheckAnswer().execute(cv);
                        } else if (question.getId().equalsIgnoreCase(
                                QNO_OLD_PASS + "")
                                && helper.isLoggedIn()) {
                            // loadQuestion1();
                            checkLoadQuestion();
                        } else {
                            ChatMessage cma = new ChatMessage(false,
                                    question.getAnswer(), question.getId(),
                                    question.getAnswerTimestamp(), "", false);
                            if (currentQ == 12) {
                                if (cma.message.equalsIgnoreCase("m")) {
                                    cma.message = "Male";
                                } else {
                                    cma.message = "Female";
                                }
                            }
                            sendChat(cma);
                        }
                    } else if (question.getId().equalsIgnoreCase(
                            QNO_OLD_PASS + "")
                            && helper.isLoggedIn()) {
                        // loadQuestion1();
                        checkLoadQuestion();
                    } else {
                        setRestrictions();
                    }
                }
            } else if (question != null
                    && question.getTitle().equalsIgnoreCase("")) {
                if (question.getId().equalsIgnoreCase("17")
                        || question.getId().equalsIgnoreCase("26")
                        || question.getId().equalsIgnoreCase("37")
                        || question.getId().equalsIgnoreCase("52")
                        || question.getId().equalsIgnoreCase("69")
                        || question.getId().equalsIgnoreCase("109")) {
                    // TODO show summary
                    showSummary();
                } else {
                    checkLoadQuestion();
                }
            } else {
                hideInputs();
                // chatInput.setEnabled(false);
            }
        }
    }

    private String getGreeting() {
        String toReturn = "";
        Calendar c = Calendar.getInstance();
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay >= 0 && timeOfDay < 12) {
            toReturn = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            toReturn = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            toReturn = "Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            toReturn = "Hello";
        }
        return toReturn;
    }

    private void loadAfterDelay() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQuestion1();
            }
        }, 1000);
    }

    private boolean isNextAnswered(int q_no) {
        boolean toReturn = false;
        Question q = helper.getQuestion(q_no);
        if (q != null) {
            if (q.getQType() == 0) {
                toReturn = isNextAnswered(q_no + 1);
            } else {
                if (q.getIsValid() == 1) {
                    toReturn = true;
                }
            }
        }
        return toReturn;
    }

    private void hideInputs() {
        hideKeyboard(chatInput);
        chatInput.setOnFocusChangeListener(null);
        dropDown.setVisibility(View.GONE);
        chatInputLayout.setVisibility(View.GONE);
        buttonsLayout.setVisibility(View.GONE);
        dateLayout.setVisibility(View.GONE);
    }

    private void setRestrictions() {
        removeTextChangeListener();
        validating = false;
        hideError();
        hideInputs();
        switch (question.getInputType()) {
            case 1:
            case 6:// alphanumeric
            case 8:// ALPHA_AND_NUMBERS
                setChatInputOn();
                if (question.getId().equalsIgnoreCase(QNO_NEW_PASS + "")
                        || question.getId().equalsIgnoreCase(QNO_OLD_PASS + "")) {
                    chatInput.setInputType(InputType.TYPE_CLASS_TEXT
                            | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
//                    chatInput.setTransformationMethod(PasswordTransformationMethod
//                            .getInstance());
                } else {
                    chatInput.setInputType(InputType.TYPE_CLASS_TEXT);
                }
                break;
            case 2:// only alphabets
                setChatInputOn();
                // chatInput.setKeyListener(DigitsKeyListener
                // .getInstance("abcdefghijklmnopqrstuvwxyz"));
                chatInput.setInputType(InputType.TYPE_CLASS_TEXT);
                break;
            case 3:// only numbers
                setChatInputOn();
                chatInput.setInputType(InputType.TYPE_CLASS_NUMBER);
                break;
            case 4:// drop down
                dropDown.setVisibility(View.VISIBLE);
                ArrayList<String> spinnerData = helper.getStates();
                spinnerData.add(0, "Select State");
                ArrayAdapter<String> adapter = new ArrayAdapter<>(
                        getApplicationContext(), R.layout.dropdown_item,
                        R.id.item_text, spinnerData);
                dropDown.setAdapter(adapter);
                dropDown.setOnItemSelectedListener(dropdownSelectListener);
                break;
            case 5:// button options
                buttonsLayout.setVisibility(View.VISIBLE);
                ArrayList<String> options = question.getOptions();
                ButtonsGridViewAdapter btns = new ButtonsGridViewAdapter(
                        getApplicationContext(), 0, this, options);
                buttonsLayout.setAdapter(btns);
                break;
            case 7:// Calender
                showDateSpinners();
//                chatInputLayout.setVisibility(View.VISIBLE);
//                showDatePicker();
//                chatInput.setOnFocusChangeListener(new OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View v, boolean hasFocus) {
//                        if (hasFocus) {
//                            showDatePicker();
//                        } else {
//                            // showToast("lost the focus");
//                        }
//                    }
//                });
                break;

            default:
                break;
        }
        scrollMyListViewToBottom(-1);
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    private void setSpinnerPopupHeights() {
        int pHeight = dpToPx(400);
        try {
            Field popup = yearSpinner.getClass().getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(yearSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(pHeight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            Field popup = monthSpinner.getClass().getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(monthSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(pHeight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            Field popup = dateSpinner.getClass().getDeclaredField("mPopup");
            popup.setAccessible(true);

            // Get private mPopup member variable and try cast to ListPopupWindow
            ListPopupWindow popupWindow = (ListPopupWindow) popup.get(dateSpinner);

            // Set popupWindow height to 500px
            popupWindow.setHeight(pHeight);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void showDateSpinners() {
        Calendar today = Calendar.getInstance();
        dateLayout.setVisibility(View.VISIBLE);
        ArrayList<String> yearData = new ArrayList<>();
        for (int i = 1920; i <= 2000/*today.get(Calendar.YEAR)*/; i++) {
            yearData.add(i + "");
        }
        ArrayAdapter<String> yearAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item,
                R.id.item_text, yearData);
        yearSpinner.setAdapter(yearAdapter);
        yearSpinner.setOnItemSelectedListener(dateSelectListener);
        ArrayList<String> monthData = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            today.set(Calendar.MONTH, i);
            monthData.add(today.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.US));
        }
        ArrayAdapter<String> monthAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item,
                R.id.item_text, monthData);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setOnItemSelectedListener(dateSelectListener);
        ArrayList<String> dateData = new ArrayList<>();
        for (int i = 1; i <= today.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dateData.add(i + "");
        }
        ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(
                getApplicationContext(), R.layout.dropdown_item,
                R.id.item_text, dateData);
        dateSpinner.setAdapter(dateAdapter);
        today = Calendar.getInstance();
        yearSpinner.setSelection(yearData.size() - 13);
        monthSpinner.setSelection(0);
        dateSpinner.setSelection(0);
//        monthSpinner.setSelection(today.get(Calendar.MONTH));
//        dateSpinner.setSelection(today.get(Calendar.DAY_OF_MONTH) + 1);
//        dateSpinner.setOnItemSelectedListener(dateSelectListener);
    }

    private OnItemSelectedListener dateSelectListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            Calendar selected = Calendar.getInstance();
            selected.set(yearSpinner.getSelectedItemPosition() + 1900, monthSpinner.getSelectedItemPosition(), 1);
            ArrayList<String> dateData = new ArrayList<>();
            for (int i = 1; i <= selected.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
                dateData.add(i + "");
            }
            ArrayAdapter<String> dateAdapter = new ArrayAdapter<>(
                    getApplicationContext(), R.layout.dropdown_item,
                    R.id.item_text, dateData);
            dateSpinner.setAdapter(dateAdapter);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    // @Override
    // protected Dialog onCreateDialog(int id) {
    private void showDatePicker() {
        hideKeyboard(chatInput);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR), month = calendar
                .get(Calendar.MONTH), day = calendar.get(Calendar.DAY_OF_MONTH);
        // return new DatePickerDialog(getApplicationContext(), myDateListener,
        // year, month, day);
        DatePickerDialog dpd = new DatePickerDialog(this, myDateListener, year,
                month, day);
//        DatePickerDialog dpd = new DatePickerDialog(this, android.R.style.Theme_Material_Dialog,myDateListener, year,
//                month, day);
        dpd.getDatePicker().setMaxDate(System.currentTimeMillis());
        dpd.setButton(DialogInterface.BUTTON_NEGATIVE,
                getString(R.string.cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == DialogInterface.BUTTON_NEGATIVE) {
                            // Do Stuff
                        }
                    }
                });
        dpd.setTitle("Set Date Of Birth");
        dpd.setCancelable(false);
        dpd.show();
        setChatInputOn();
        sendFocusOnButton();
    }

    private void sendFocusOnButton() {
        sendButton.setFocusable(true);
        sendButton.setFocusableInTouchMode(true);// /add this line
        sendButton.requestFocus();
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            // showDate(year, month + 1, day);
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, month);
            String monStr = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT,
                    Locale.US);
            String dayStr = day < 10 ? "0" + day : day + "";
            chatInput.setText(dayStr + "-" + monStr + "-" + year);
            sendFocusOnButton();
        }
    };

    private TextWatcher noSpecialChars = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (!isCheckingTextChange) {
                isCheckingTextChange = true;
//            removeTextChangeListener();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (Character.isLetterOrDigit(c)) {
                        builder.append(c);
                    }
                }
                chatInput.setText(builder.toString());
                chatInput.setSelection(builder.length());
//            addTextChangeListener(2);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private TextWatcher onlyAlpha = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            InputMethodSubtype ims=imm.getCurrentInputMethodSubtype();
            if (!isCheckingTextChange) {
                isCheckingTextChange = true;
//                removeTextChangeListener();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < s.length(); i++) {
                    char c = s.charAt(i);
                    if (Character.isLetter(c)/* || c == ' '*/) {
                        builder.append(c);
                    }
                }
                chatInput.setText(builder.toString());
                chatInput.setSelection(builder.length());
//                addTextChangeListener(1);
                isCheckingTextChange = false;
            }
//            imm.setCurrentInputMethodSubtype(ims);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void removeTextChangeListener() {
        try {
            chatInput.removeTextChangedListener(onlyAlpha);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            chatInput.removeTextChangedListener(noSpecialChars);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addTextChangeListener(int code) {
        switch (code) {
            case 1:
                chatInput.addTextChangedListener(onlyAlpha);
                break;
            case 2:
                chatInput.addTextChangedListener(noSpecialChars);
                break;

            default:
                chatInput.addTextChangedListener(onlyAlpha);
                break;
        }
    }

    private void setChatInputOn() {
        chatInputLayout.setVisibility(View.VISIBLE);
        InputFilter[] fArray;
        // InputFilter[] fArray = new InputFilter[1];
        if (currentQ == QNO_OLD_PASS || currentQ == QNO_NEW_PASS) {
            fArray = new InputFilter[2];
            fArray[1] = new NoSpaceInputFilter();
//            removeTextChangeListener();
        } else if (question.getInputType() == AnswerInputTypes.ONLY_ALPHABETS
                && currentQ != QNO_PAN && currentQ != QNO_TAN) {
            fArray = new InputFilter[1];
            addTextChangeListener(1);
//            chatInput.addTextChangedListener(onlyAlpha);
//            fArray = new InputFilter[2];
//            fArray[1] = new OnlyAlphabetsInputFilter();
        } else if (question.getInputType() == AnswerInputTypes.ALPHA_AND_NUMBERS) {
//            fArray = new InputFilter[2];
//            fArray[1] = new NoSpecialCharsInputFilter();
            fArray = new InputFilter[1];
            addTextChangeListener(2);
        } else {
            fArray = new InputFilter[1];
        }
        // set length filter later
        fArray[0] = new InputFilter.LengthFilter(question.getMaxLength());
        chatInput.setFilters(fArray);
        chatInput.requestFocus();
        showKeyboard(chatInput);
    }

    private OnClickListener optionsClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            updateAnswer(v.getTag().toString(), true);
        }
    };

    private OnItemSelectedListener dropdownSelectListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            if (position != 0) {
                String answer = dropDown.getAdapter().getItem(position)
                        .toString();
                updateAnswer(answer, true);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
            // TODO Auto-generated method stub
        }
    };

    private void copyAnswers(int from, int to, int into) {
        for (int i = from; i <= to; i++) {
            helper.copyAnswer(i + "", into + "");
            into++;
        }
    }

    private void removeAnswers(int from, int to) {
        for (int i = from; i <= to; i++) {
            helper.removeAnswer(i + "");
        }
    }

    private void sendChat(ChatMessage cm) {
        if (question.getId().equalsIgnoreCase(QNO_HOUSES + "")
                && (cm.message.equalsIgnoreCase("zero") || cm.message
                .equalsIgnoreCase("multiple"))) {
            removeAnswers(QNO_HOUSES + 1, QNO_HOUSES + 2);// remove house
            // details
        }
        // bank details
        if (question.getId().equalsIgnoreCase(QNO_BANK_1 + "")
                && cm.message.equalsIgnoreCase("no")) {
            helper.updateAnswer(QNO_BANK_1 + "", "No", false, 0);
            removeAnswers(QNO_BANK_1 + 1, QNO_BANK_END - 1);
        }
        if (question.getId().equalsIgnoreCase(QNO_BANK_2 + "")
                && cm.message.equalsIgnoreCase("no")) {
            helper.updateAnswer(QNO_BANK_2 + "", "No", false, 0);
            removeAnswers(QNO_BANK_2 + 1, QNO_BANK_END - 1);
        }
        if (question.getId().equalsIgnoreCase(QNO_BANK_3 + "")
                && cm.message.equalsIgnoreCase("no")) {
            helper.updateAnswer(QNO_BANK_3 + "", "No", false, 0);
            removeAnswers(QNO_BANK_3 + 1, QNO_BANK_END - 1);
        }// /////////
        chatAdapter.insert(cm, insertPos);
        if (insertPos != -1) {
            insertPos = -1;
            currentQ = previousQ;
            previousQ = -1;
            question = helper.getQuestion(currentQ);
            if (question != null) {
                currentQId = question.getId();
                setRestrictions();
            } else {
                hideInputs();
            }
        } else {
            if (!cm.left && loadNext) {
                updateProgress();
//                if (((question.getId().equalsIgnoreCase("77")
//                        || question.getId().equalsIgnoreCase("83") || question
//                        .getId().equalsIgnoreCase("89")) && question
//                        .getAnswer().equalsIgnoreCase("no"))
//                        || question.getId().equalsIgnoreCase("95")) {
//                    showSummary();
//                } else {
                checkLoadQuestion();
//                }
            }
        }
    }

    private void showSummary() {
        showingReport = true;
        int from = -1, to = -1;
        ArrayList<ReportRow> row = new ArrayList<>();
        if (currentQ == SECTION_END_1) {
            Question q = helper.getQuestion(QNO_1 + 2);
            row.add(new ReportRow(q.getShortTitle(), q.getAnswer()));
            from = 14;
            to = 16;
        } else if (currentQ == SECTION_END_2) {
            from = 21;
            to = 26;
        } else if (currentQ == SECTION_END_3) {
            from = 31;
            to = 39;
        } else if (currentQ == SECTION_END_4) {
            from = 47;
            to = 50;
        } else if (currentQ == SECTION_END_5) {
            from = 57;
            to = 59;
        } else if (currentQ == SECTION_END_6) {
            from = 64;
            to = 90;
        } else if (currentQ == SECTION_END_7) {
            from = -1;
            to = -1;
        } else if (currentQ == SECTION_END_8) {
            from = 101;
            to = 105;
        }
        if (from != -1 && to != -1) {
            for (int i = from; i <= to; i++) {
                Question q = helper.getQuestion(i);
                String shortTitle = q.getShortTitle();
                String title = q.getTitle();
                String answer = q.getAnswer();
                if (!(title.equalsIgnoreCase("") || answer.equalsIgnoreCase("") || shortTitle
                        .equalsIgnoreCase("")))
                    row.add(new ReportRow(q.getShortTitle(), q.getAnswer()));
            }
            int banksCount = 1;
            if (currentQ == SECTION_END_8) {
                if (helper.getQuestion(QNO_BANK_1).getAnswer()
                        .equalsIgnoreCase("yes")) {
                    banksCount++;
                }
                if (helper.getQuestion(QNO_BANK_2).getAnswer()
                        .equalsIgnoreCase("yes")) {
                    banksCount++;
                }
                if (helper.getQuestion(QNO_BANK_3).getAnswer()
                        .equalsIgnoreCase("yes")) {
                    banksCount++;
                }
            }
            if (banksCount > 1) {
                row.add(new ReportRow("Total Bank Accounts", banksCount + ""));
            }
        }
        addSummary(row);
    }

    private void addSummary(ArrayList<ReportRow> row) {
        helper.checkUploadAnswers(1);
        ChatMessage cm = new ChatMessage(currentQ, row);
        chatAdapter.insert(cm, insertPos);
        scrollMyListViewToBottom(-1);
        // /////
        if (question.getIsValid() != 1 || currentQ == SECTION_END_9) {
            hideInputs();
            ArrayList<String> options = new ArrayList<>();
            if (currentQ == SECTION_END_9) {
                if (showEFileButton) {
                    if (!PrefsManager.getPrefs(ChatActivity.this, Constants.RETURN_FILED, false)) {
                        fileReturn = true;
                        options.add("e-File My Return Now");
                    } else {
                        checkLoadQuestion();
                    }
                } else {
                    ChatMessage m = new ChatMessage(true, getString(R.string.efile_error_text), currentQ + "", "", "", false);
                    sendChat(m);
                }
            } else {
                options.add("Proceed");
            }
            if (options.size() > 0) {
                buttonsLayout.setVisibility(View.VISIBLE);
                ButtonsGridViewAdapter btns = new ButtonsGridViewAdapter(
                        getApplicationContext(), 0, this, options);
                buttonsLayout.setAdapter(btns);
            }
        } else {
            checkLoadQuestion();
        }
    }

    private void scrollMyListViewToBottom(final int position) {
        // showToast("Scroll: " + position);
        chatView.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                if (position != -1) {
                    chatView.setSelection(position);
                    // chatView.smoothScrollToPosition(position);
                } else {
                    // chatView.smoothScrollToPosition(chatAdapter.getCount() -
                    // 1);
                    chatView.setSelection(chatAdapter.getCount() - 1);
                }
            }
        }, 500);
    }

    private OnEditorActionListener actionListener = new OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEND) {
                if (!validating)
                    validateAnswer();
                return true;
            } else {
                return false;
            }
        }
    };

    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validating)
                validateAnswer();
        }
    };

    private void validateAnswer() {
        validating = true;
        boolean validation = true;
        String errMsg = "";
        String answer = chatInput.getText().toString().trim();
        if (question.getInputType() == AnswerInputTypes.CALENDER) {
            String dateStr = dateSpinner.getSelectedItem().toString().length() < 2 ? "0" + dateSpinner.getSelectedItem().toString() : dateSpinner.getSelectedItem().toString();
            answer = dateStr + "-" + monthSpinner.getSelectedItem().toString() + "-" + yearSpinner.getSelectedItem().toString();
        }
        if (!(answer.length() >= question.getMinLength() && answer.length() <= question
                .getMaxLength())) {
            validation = false;
            errMsg += "Answer should be min " + question.getMinLength()
                    + " and max " + question.getMaxLength() + " characters";
        } else if (question.getMinLength() != 0
                && question.getRegex().length() > 0
                && !answer.toLowerCase().matches(
                question.getRegex().toLowerCase())) {
            validation = false;
            errMsg += question.getHelp1() + "\n" + question.getHelp2();
        }
        if (question.getInputType() == AnswerInputTypes.CALENDER
                && answer.equalsIgnoreCase("")) {
            validation = false;
        }
        if (currentQ == QNO_TAX_DEDUCTED) {
            int sal = 0;
            int tax = 0;
            try {
                sal = Integer.parseInt(helper.getQuestion(QNO_TAX_DEDUCTED - 1)
                        .getAnswer());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                tax = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (sal != 0 && tax > sal / 2) {
                validation = false;
                errMsg = "Tax Deducted is greater than Chargeable Income";
            }
        }
        if (currentQ == QNO_TAX_DEDUCTED - 1) {
            int sal = 0;
            int tax = 0;
            try {
                sal = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            try {
                tax = Integer.parseInt(helper.getQuestion(QNO_TAX_DEDUCTED)
                        .getAnswer());
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            if (sal != 0 && tax > sal / 2) {
                // helper.updateAnswer((QNO_TAX_DEDUCTED) + "", "", false, 0);
                helper.removeAnswer(QNO_TAX_DEDUCTED + "");
            }
        }
        if (validation) {
            removeTextChangeListener();
            if (question.getId().equalsIgnoreCase(QNO_EMAIL_ID + "")) {
                ContentValues cv = new ContentValues();
                cv.put("email_id", answer);
                new CheckAnswer().execute(cv);
            } else if (question.getId().equalsIgnoreCase(QNO_OLD_PASS + "")) {
                ContentValues cv = new ContentValues();
                cv.put("email_id", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                cv.put("password", answer);
                new CheckAnswer().execute(cv);
            } else if (question.getId().equalsIgnoreCase(QNO_NEW_PASS + "")) {
                ContentValues cv = new ContentValues();
                cv.put("email_id", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                cv.put("password", answer);
                new CheckAnswer().execute(cv);
            } else {
                updateAnswer(answer, true);
                chatInput.setText("");
            }
            hideError();
        } else {
            if (!question.getHelp1().equalsIgnoreCase("")) {
                errMsg = question.getHelp1();
            }
            showError(errMsg.trim());
            validating = false;
        }
    }

    private void showError(String err) {
        errorText.setText(err);
        errorText.setVisibility(View.VISIBLE);
    }

    private void hideError() {
        errorText.setText("");
        errorText.setVisibility(View.GONE);
    }

    private void updateProgress() {
        progressText.setText((currentQ * 100 / TOTAL_QUESTIONS) + "%");
    }

    private void updateAnswer(String answer, boolean updateTimestamp) {
        if (helper.updateAnswer(question.getId(), answer, updateTimestamp, 0) != -1) {
            updateProgress();
            ChatMessage cm = new ChatMessage(false, answer, question.getId(),
                    helper.getQuestion(currentQ).getAnswerTimestamp(), "",
                    false);
//            if (question.getInputType() == AnswerInputTypes.BUTTON_OPTIONS) {
//                Question q = helper.getQuestion(currentQ);
//                cm.message = q.getAnswer();
//            }
            sendChat(cm);
        } else {
            showToast("Error saving answer, please try again");
        }
    }

    private void showToast(final String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ChatActivity.this, text, Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        // imm.showSoftInput(view, 0);
    }

    private void showKeyboard(View view) {
        scrollMyListViewToBottom(-1);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        // imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
        imm.showSoftInput(view, 0);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks whether a hardware keyboard is available
        if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
//            Toast.makeText(this, "keyboard visible", Toast.LENGTH_SHORT).show();
            scrollMyListViewToBottom(-1);
        } else if (newConfig.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_YES) {
//            Toast.makeText(this, "keyboard hidden", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onEditClicked(int pos, String id) {
        fileReturn = false;
        showingReport = false;
        chatAdapter.removeAllAfter(id);
        question = helper.getQuestion(Integer.parseInt(id));
        chatInput.setText(question.getAnswer());
        chatInput.setSelection(chatInput.getText().length());
        helper.removeAnswer(id);
        currentQ = Integer.parseInt(id) - 1;
        loadQuestion1();
        scrollMyListViewToBottom(pos - 1);
    }

    @Override
    public void onHelpClicked(int pos, String id) {
        PopupUtils.createPopup(ChatActivity.this, question.getShortTitle(),
                question.getHelp2());
    }

    @Override
    public void onSkipClicked(int pos, String id) {
        if (question.getInputType() == AnswerInputTypes.ALPHA_AND_NUMBERS
                || question.getInputType() == AnswerInputTypes.ONLY_ALPHABETS) {
//            chatInput.setText("Skipped");
            chatInput.setText("");
        } else {
//            chatInput.setText("0");
            chatInput.setText("");
        }
        if (!validating)
            validateAnswer();
    }

    @Override
    public void onForgotPassClicked(int pos, String id) {
        isForgotPass = true;
        showPasswordDialog("Password Reset", "Are you sure you want to reset your password?", "Reset Password", "Try Again");
//        isForgotPass = true;
//        ContentValues cv = new ContentValues();
//        cv.put("email_ID", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
//        new CheckAnswer().execute(cv);
    }

    @Override
    public void onOptionClicked(String id) {
        if (fileReturn) {
            showPasswordDialog("E-File Return", "Do you want to e-file your return now?", "E-File Now", "Later");
//            new EFile().execute();
        } else if (showingReport) {
            helper.updateAnswer(question.getId(), "Done", true, 1);
            showingReport = false;
            checkLoadQuestion();
        } else {
            updateAnswer(id, true);
        }
    }

    private void showProgressDialog() {
        if (!ChatActivity.this.isFinishing() && !pDialog.isShowing()) {
            pDialog = new ProgressDialog(ChatActivity.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.show();
        }
    }

    public class GetComputation extends AsyncTask<ContentValues, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (!ChatActivity.this.isFinishing()) {
                pDialog = new ProgressDialog(ChatActivity.this);
                pDialog.setCancelable(false);
                pDialog.setTitle("Please wait");
                pDialog.show();
            }
        }

        @Override
        protected String doInBackground(ContentValues... cvs) {
            String url = Constants.GET_COMPUTATION;
            ContentValues cv = new ContentValues();
            cv.put(Constants.HCUID, Constants.HCUID_VAL);
            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
//            cv.put("token", PrefsManager.getPrefs(getApplicationContext(), Constants.TOKEN, ""));
            PostManager.access_token = PrefsManager.getPrefs(getApplicationContext(), Constants.TOKEN, "");
            String result = PostManager.excutePost(url, cv, true);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            chatInput.setText("");
            if (result != null) {
//                showToast(result);
                try {
                    ArrayList<ReportRow> row = new ArrayList<>();
                    JSONArray resp = new JSONArray(result);
                    for (int i = 0; i < resp.length(); i++) {
                        JSONObject o = resp.getJSONObject(i);
                        row.add(new ReportRow(o.getString("description"), o.getString("column3")));
                        if (o.getString("description").equalsIgnoreCase("Tax payable") && !o.getString("column3").equalsIgnoreCase("NIL")) {
                            showEFileButton = false;
                        } else {
//                            if (!PrefsManager.getPrefs(ChatActivity.this, Constants.RETURN_FILED, false)) {
//                                showEFileButton = true;
//                            } else {
//                                checkLoadQuestion();
//                            }
                        }
                    }
                    addSummary(row);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showToast("Error, try again later");
            }
            if (!ChatActivity.this.isFinishing()) {
                pDialog.cancel();
            }
        }
    }

    public class EFile extends AsyncTask<ContentValues, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChatActivity.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.show();
        }

        @Override
        protected String doInBackground(ContentValues... cvs) {
            String url = Constants.E_FILE;
            ContentValues cv = new ContentValues();
            cv.put(Constants.HCUID, Constants.HCUID_VAL);
            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
//            cv.put("token", PrefsManager.getPrefs(getApplicationContext(), Constants.TOKEN, ""));
            PostManager.access_token = PrefsManager.getPrefs(getApplicationContext(), Constants.TOKEN, "");
            String result = PostManager.excutePost(url, cv, true);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            chatInput.setText("");
            fileReturn = false;
            if (result != null) {
                PrefsManager.savePrefs(ChatActivity.this, Constants.RETURN_FILED, true);
//                showToast(result);
                checkLoadQuestion();
                try {
                    JSONArray resp = new JSONArray(result);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                showToast("Error, try again later");
            }
            pDialog.cancel();
        }
    }

    public class CheckAnswer extends AsyncTask<ContentValues, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ChatActivity.this);
            pDialog.setCancelable(false);
            pDialog.setTitle("Please wait");
            pDialog.show();
        }

        @Override
        protected String doInBackground(ContentValues... cvs) {
            String url = "";
            if (isForgotPass) {
                url = Constants.CHANGE_PASSWORD;
            } else if (question.getId().equalsIgnoreCase(QNO_EMAIL_ID + "")) {
                url = Constants.CHECK_EMAIL;
            } else if (question.getId().equalsIgnoreCase(QNO_OLD_PASS + "")) {
                url = Constants.CHECK_LOGIN;
            } else if (question.getId().equalsIgnoreCase(QNO_NEW_PASS + "")) {
                url = Constants.CREATE_ACCOUNT;
            }
            ContentValues cv = cvs[0];
            cv.put(Constants.HCUID, Constants.HCUID_VAL);
            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
            String result = PostManager.excutePost(url, cv, false);
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null) {
                if (isForgotPass) {
                    isForgotPass = false;
                    checkForgotPassResponse(result);
                } else if (question.getId().equalsIgnoreCase(QNO_EMAIL_ID + "")) {
                    checkEmailResponse(result);
                } else if (question.getId().equalsIgnoreCase(QNO_OLD_PASS + "")) {
                    checkLoginResponse(result);
                } else if (question.getId().equalsIgnoreCase(QNO_NEW_PASS + "")) {
                    checkCreateAccountResponse(result);
                }
            } else {
                isForgotPass = false;
                showToast("Error, try again later");
            }
            chatInput.setText("");
            validating = false;
            pDialog.cancel();
        }
    }

    public class GetToken extends AsyncTask<ContentValues, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(ContentValues... cvs) {
            String toReturn = "0";
            String url = Constants.AUTH_TOKEN;
            ContentValues cv = cvs[0];
            cv.put("grant_type", "password");
            cv.put(Constants.HCUID, Constants.HCUID_VAL);
            cv.put(Constants.HCPASS, Constants.HCPASS_VAL);
            String result = PostManager.excutePost(url, cv, false);
            if (result != null) {
                try {
                    JSONObject resp = new JSONObject(result);
                    PrefsManager.savePrefs(getApplicationContext(),
                            Constants.TOKEN, resp.getString(Constants.TOKEN));
                    PostManager.access_token = resp.getString(Constants.TOKEN);
                    if (!PrefsManager.getPrefs(getApplicationContext(),
                            "questions_fetched", false)) {
                        PrefsManager.savePrefs(getApplicationContext(),
                                "questions_fetched", true);
                        String quests = PostManager
                                .excuteGet(Constants.GET_SAVED_QUESTIONS);
                        JSONArray quess = new JSONArray(quests);
                        for (int i = 0; i < quess.length(); i++) {
                            JSONObject q = quess.getJSONObject(i);
                            String ans = getLongAnswer(q.getString("answer"),
                                    q.getString("qno"));
                            helper.updateAnswer(q.getString("qno"), ans, true,
                                    1);
                        }
                    }
                    toReturn = "1";
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return toReturn;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result.equalsIgnoreCase("1")) {
                loadNext = true;
                checkLoadQuestion();
            } else {
                showToast("Error getting data, try again later");
            }
        }
    }

    public static String getShortAnswer(String answer, String question_id) {
        String shortAnswer = answer;
        if (question_id.equalsIgnoreCase("12")
                && answer.equalsIgnoreCase(Constants.TEXT_MALE)) {
            shortAnswer = "M";
        } else if (question_id.equalsIgnoreCase("12")
                && answer.equalsIgnoreCase(Constants.TEXT_FEMALE)) {
            shortAnswer = "F";
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("Ordinarily resident")) {
            shortAnswer = "RES";
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("Non-ordinarily resident")) {
            shortAnswer = "NRI";
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("non-resident")) {
            shortAnswer = "NOR";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("Government")) {
            shortAnswer = "GOV";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("Public Sector Undertaking")) {
            shortAnswer = "PSU";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("Other")) {
            shortAnswer = "OTH";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("Zero")) {
            shortAnswer = "0";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("One")) {
            shortAnswer = "1";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("Multiple")) {
            shortAnswer = "2";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("Self occupied")) {
            shortAnswer = "SELF-OCCU";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("Vacant")) {
            shortAnswer = "VACANT";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("Letout")) {
            shortAnswer = "LETOUT";
        } else if (answer.equalsIgnoreCase("Yes")) {
            shortAnswer = "1";
        } else if (answer.equalsIgnoreCase("No")) {
            shortAnswer = "0";
        } else if (answer.equalsIgnoreCase("Savings")) {
            shortAnswer = "SAV";
        } else if (answer.equalsIgnoreCase("Current")) {
            shortAnswer = "CUR";
        }
        return shortAnswer;
    }

    private String getLongAnswer(String answer, String question_id) {
        String longAnswer = answer;
        if (question_id.equalsIgnoreCase((QNO_5 - 1) + "")
                && answer.equalsIgnoreCase("M")) {
            longAnswer = Constants.TEXT_MALE;
        } else if (question_id.equalsIgnoreCase((QNO_5 - 1) + "")
                && answer.equalsIgnoreCase("F")) {
            longAnswer = Constants.TEXT_FEMALE;
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("RES")) {
            longAnswer = "Ordinarily resident";
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("NRI")) {
            longAnswer = "Non-ordinarily resident";
        } else if (question_id.equalsIgnoreCase("31")
                && answer.equalsIgnoreCase("NOR")) {
            longAnswer = "Non-Resident";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("GOV")) {
            longAnswer = "Government";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("PSU")) {
            longAnswer = "Public Sector Undertaking";
        } else if (question_id.equalsIgnoreCase("44")
                && answer.equalsIgnoreCase("OTH")) {
            longAnswer = "Other";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("0")) {
            longAnswer = "Zero";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("1")) {
            longAnswer = "One";
        } else if (question_id.equalsIgnoreCase("45")
                && answer.equalsIgnoreCase("2")) {
            longAnswer = "Multiple";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("SELF-OCCU")) {
            longAnswer = "Self occupied";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("VACANT")) {
            longAnswer = "Vacant";
        } else if (question_id.equalsIgnoreCase("46")
                && answer.equalsIgnoreCase("LETOUT")) {
            longAnswer = "Letout";
        } else if (answer.equalsIgnoreCase("1")) {
            longAnswer = "Yes";
        } else if (answer.equalsIgnoreCase("0")) {
            longAnswer = "No";
        } else if (answer.equalsIgnoreCase("SAV")) {
            longAnswer = "Savings";
        } else if (answer.equalsIgnoreCase("CUR")) {
            longAnswer = "Current";
        }
        return longAnswer;
    }

    private void checkEmailResponse(String result) {
        JSONObject resp;
        try {
            resp = new JSONObject(result);
            if (resp.getBoolean("isEmail")) {
                // load question 3
                // do not change
            } else {
                // load question 4
                // make it 3 load next will add 1 to it
                currentQ = QNO_OLD_PASS;
            }
            if (question.getAnswer().equalsIgnoreCase("")) {
                updateAnswer(chatInput.getText().toString().trim(), true);
            } else {
                updateAnswer(question.getAnswer(), false);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkForgotPassResponse(String result) {
        JSONObject resp;
        try {
            resp = new JSONObject(result);
            PopupUtils.createPopup(ChatActivity.this, "Password Reset", resp.getString("errorMessage"));
        } catch (JSONException e) {
            e.printStackTrace();
            showToast("Error, try again later");
        }
    }

    private void checkLoginResponse(String result) {
        // {"client_id":0,"message":"Please enter correct USER NAME and PASSWORD.","token":null}
        JSONObject resp;
        try {
            resp = new JSONObject(result);
            if (resp.getInt("client_id") != 0) {
                helper.addLogin(helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                currentQ = QNO_SKIP_CONGRATS;// skip congrats
                loadNext = false;
                String answer = "";
                if (question.getAnswer().equalsIgnoreCase("")) {
                    answer = chatInput.getText().toString().trim();
                    updateAnswer(answer, true);
                } else {
                    answer = question.getAnswer();
                    updateAnswer(answer, false);
                }
                ContentValues cv = new ContentValues();
                cv.put("email_id", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                cv.put("password", answer);
                new GetToken().execute(cv);
            } else {
                isForgotPass = true;
                showPasswordDialog("Login Error", resp.getString("message"), "Reset Password", "Try Again");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showPasswordDialog(String titleStr, String message, String okBtnText, String cancelBtntext) {
        final Dialog dialog = new Dialog(this);
        dialog.getWindow().setBackgroundDrawable(
                new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.forgot_pass);
        dialog.setTitle(null);

        TextView title = (TextView) dialog.findViewById(R.id.title);
        TextView desc = (TextView) dialog.findViewById(R.id.desc);

        title.setText(titleStr);
        desc.setText(message);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setText(okBtnText);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isForgotPass) {
                    ContentValues cv = new ContentValues();
                    cv.put("email_ID", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                    new CheckAnswer().execute(cv);
                } else if (fileReturn) {
                    dialog.dismiss();
                    new EFile().execute();
                }
            }
        });
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.dialogButtonCancel);
        dialogButtonCancel.setText(cancelBtntext);
        // if button is clicked, close the custom dialog
        dialogButtonCancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                isForgotPass = false;
//                fileReturn = false;
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void checkCreateAccountResponse(String result) {
        // {"client_id":0,"message":"E-MAIL ALREADY EXISTS","token":""}
        JSONObject resp;
        try {
            resp = new JSONObject(result);
            if (resp.getInt("client_id") != 0) {
                // success
                helper.addLogin(helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                // currentQ = 5;// skip congrats
                loadNext = false;
                String answer = "";
                if (question.getAnswer().equalsIgnoreCase("")) {
                    answer = chatInput.getText().toString().trim();
                    updateAnswer(answer, true);
                } else {
                    answer = question.getAnswer();
                    updateAnswer(answer, false);
                }
                ContentValues cv = new ContentValues();
                cv.put("email_id", helper.getQuestion(QNO_EMAIL_ID).getAnswer());
                cv.put("password", answer);
                new GetToken().execute(cv);
            } else {
                showToast("Account could not be created: "
                        + resp.getString("message"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setMenuPopup() {
        // showToast("menu more set");
        _menuPopup = new PopupMenu(getApplicationContext(), _menuMore);
        // Inflating the Popup using xml file
        _menuPopup.getMenuInflater().inflate(R.menu.popup_menu,
                _menuPopup.getMenu());
        final String[] menuArr = getResources().getStringArray(
                R.array.menu_items);
        for (int i = 0; i < menuArr.length; i++) {
            _menuPopup.getMenu().add(menuArr[i]);
        }

        // registering popup with OnMenuItemClickListener
        _menuPopup
                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().toString()
                                .equalsIgnoreCase(menuArr[0])) {
                            helper.checkUploadAnswers(0);
                        } else if (item.getTitle().toString()
                                .equalsIgnoreCase(menuArr[1])) {
                            logout();
                        }
                        return true;
                    }
                });
    }

    private void logout() {
        helper.logout(getApplicationContext());
        PrefsManager.clearPrefs(this, getApplicationContext());
        Intent splashIntent = new Intent(getApplicationContext(),
                SplashActivity.class);
        splashIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(splashIntent);
        finish();
    }

    private void showMenuPopup() {
        // if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        // if (_menuPopupWindow.isShowing()) {
        // _menuPopupWindow.dismiss();
        // } else {
        // // _menuPopupWindow.showAsDropDown(_menuMore);
        // _menuPopupWindow.showAsDropDown(_menuMore, 0, 5);
        // }
        // } else {
        _menuPopup.show();
        // }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == 0)
            // showToast("scrolling stopped...");

            if (view.getId() == chatView.getId()) {
                final int currentFirstVisibleItem = chatView
                        .getFirstVisiblePosition();
                final int currentLastVisibleItem = chatView
                        .getLastVisiblePosition();
                if (currentFirstVisibleItem > mLastFirstVisibleItem) {
                    mIsScrollingUp = false;
//                    showToast("scrolling down..." + currentLastVisibleItem);
                    activateTab(currentLastVisibleItem);
                } else if (currentFirstVisibleItem < mLastFirstVisibleItem) {
                    mIsScrollingUp = true;
//                    showToast("scrolling up..." + currentFirstVisibleItem);
                    activateTab(currentFirstVisibleItem);
                }

                mLastFirstVisibleItem = currentFirstVisibleItem;
            }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
        // TODO Auto-generated method stub

    }
}