//CustomDatePickerDialog dp = 
//    new CustomDatePickerDialog(
//        context, 
//        android.R.style.Theme_Holo_Light_Dialog,  
//        datePickerListener, 
//        year, month, day
//    );

//    DatePickerDialog obj = dp.getPicker();
//    try
//    {
//        Field[] datePickerDialogFields = obj.getClass().getDeclaredFields();
//        for (Field datePickerDialogField : datePickerDialogFields)
//        { 
//            if (datePickerDialogField.getName().equals("mDatePicker"))
//            {
//                datePickerDialogField.setAccessible(true);
//                DatePicker datePicker = (DatePicker) datePickerDialogField.get(obj);
//                Field datePickerFields[] = datePickerDialogField.getType().getDeclaredFields();
//                for (Field datePickerField : datePickerFields)
//                {
//                    if(
//                        "mDayPicker".equals(datePickerField.getName()) || 
//                        "mDaySpinner".equals(datePickerField.getName())
//                    )
//                    {
//                        datePickerField.setAccessible(true);
//                        Object dayPicker = new Object();
//                        dayPicker = datePickerField.get(datePicker);
//                        ((View) dayPicker).setVisibility(View.GONE);
//                    }
//                }
//            }

//        }
//    }
//    catch(Exception ex)
//    {
//    }
//    obj.show();