//public class NewReminder extends Activity
//{
//    private static final int DATE_DIALOG_ID = 1;
//    private int year;
//    private int month;
//    private int day;
//    EditText editTextDate;
//    private String currentDate;
//    private Context context;

//    @Override
//    protected void onCreate(Bundle savedInstanceState)
//    {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.addnewreminder);
//        initialize();
//        context = getApplicationContext();
//        OnClickListener listenerDate = new OnClickListener()
//        {
//            @Override
//            public void onClick(View arg0)
//            {
//                final Calendar c = Calendar.getInstance();
//                year = c.get(Calendar.YEAR);
//                month = c.get(Calendar.MONTH);
//                day = c.get(Calendar.DAY_OF_MONTH);
//                showDialog(DATE_DIALOG_ID);
//            }
//        };
//        editTextDate.setOnClickListener(listenerDate);
//    }

//    private void initialize()
//    {
//        // TODO Auto-generated method stub
//        editTextDate = (EditText) findViewById(R.id.editTextDate);
//    }


//    private void updateDisplay()
//    {
//        currentDate = new StringBuilder().append(day).append(".")
//                .append(month + 1).append(".").append(year).toString();

//        Log.i("DATE", currentDate);
//    }

//    OnDateSetListener myDateSetListener = new OnDateSetListener()
//    {
//        @Override
//        public void onDateSet(DatePicker datePicker, int i, int j, int k)
//        {
//            year = i;
//            month = j;
//            day = k;
//            updateDisplay();
//            editTextDate.setText(currentDate);
//        }
//    };



//    @Override
//    protected Dialog onCreateDialog(int id)
//    {
//        switch (id)
//        {
//            case DATE_DIALOG_ID:
//                return new DatePickerDialog(this, myDateSetListener, year, month,
//                    day);
//        }
//        return null;
//    }
//}