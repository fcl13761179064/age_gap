package com.supersweet.luck.widget;

import android.text.TextUtils;

import com.supersweet.luck.R;
import com.supersweet.luck.application.MyApplication;
import com.supersweet.luck.utils.ToastUtils;
import com.supersweet.luck.wheelview.common.WheelData;

import java.util.ArrayList;
import java.util.List;

public class MyDatas {


    private static String sex;
    private static String s;
    private static ArrayList<WheelData> list;

    public static String sextosting(String position) {
        try {
            if (position.equals("1") || "Male".equalsIgnoreCase(position)) {
                sex = "Male";
            } else {
                sex = "Female";
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return sex;
    }

    public static String stringsextoInt(String position) {
        try {
            if (position.equals("Female")) {
                sex = "2";
            } else {
                sex = "1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return sex;
    }


    public static ArrayList<WheelData> AgeType() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 18; i < 88; i++) {
                item = new WheelData();
                item.setName(i + "");
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return list;
    }

    public static ArrayList<WheelData> SexTypes() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 2; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Female");
                } else if (i == 1) {
                    item.setName("Male");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }

    public static String bodyType(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("Athletic");
            bodyType.add("A few extra pounds");
            bodyType.add("Average");
            bodyType.add("Curvy");
            bodyType.add("Slim");
            bodyType.add("Full-figured");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);

        } catch (Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return s;
    }

    public static String HairType(String position) {
        try {

            List<String> bodyType = new ArrayList<>();
            bodyType = new ArrayList<>();
            bodyType.add("Auburn");
            bodyType.add("Black");
            bodyType.add("Blonde");
            bodyType.add("Dark brown");
            bodyType.add("Grey");
            bodyType.add("Red");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }

        return s;
    }

    public static String RelationShipType(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("Single");
            bodyType.add("Married");
            bodyType.add("Divorced");
            bodyType.add("Windowed");
            bodyType.add("Separated");
            bodyType.add("Open relationship");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return s;
    }

    public static String educationType(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("High school");
            bodyType.add("Some college");
            bodyType.add("Associates degree");
            bodyType.add("Bachelors degree");
            bodyType.add("Graduate degree");
            bodyType.add("PhD./Post doctoral");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return s;
    }

    public static String ethnicity(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("Arabic/Middle eastern");
            bodyType.add("Asian");
            bodyType.add("Black/African descent");
            bodyType.add("Caribbean");
            bodyType.add("Caucasian/White");
            bodyType.add("East Indian");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return s;
    }

    public static String drinking(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("Non drinker");
            bodyType.add("Social drinker");
            bodyType.add("Heavy drinker");
            bodyType.add("Trying to quit");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return s;
    }

    public static String smoking(String position) {
        try {

            List<String> bodyType = new ArrayList<>();
            bodyType.add("Non smoker");
            bodyType.add("Light smoker");
            bodyType.add("Heavy smoker");
            bodyType.add("Trying to quit");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }

        return s;
    }

    public static String child(String position) {
        try {
            List<String> bodyType = new ArrayList<>();
            bodyType.add("No child");
            bodyType.add("One child");
            bodyType.add("Two children");
            bodyType.add("Three children");
            bodyType.add("Four children");
            bodyType.add("Over four children");
            int i = Integer.parseInt(position.substring(2)) - 1;
            s = bodyType.get(i);
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return s;
    }


    public static ArrayList<WheelData> bodyTypes() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Athletic");
                } else if (i == 1) {
                    item.setName("A few extra pounds");
                } else if (i == 2) {
                    item.setName("Average");
                } else if (i == 3) {
                    item.setName("Curvy");
                } else if (i == 4) {
                    item.setName("Slim");
                } else if (i == 5) {
                    item.setName("Full-figured");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }


    public static ArrayList<WheelData> HairTypes() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Auburn");
                } else if (i == 1) {
                    item.setName("Black");
                } else if (i == 2) {
                    item.setName("Blonde");
                } else if (i == 3) {
                    item.setName("Dark brown");
                } else if (i == 4) {
                    item.setName("Grey");
                } else if (i == 5) {
                    item.setName("Red");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }


    public static ArrayList<WheelData> RelationShipTypes() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Single");
                } else if (i == 1) {
                    item.setName("Married");
                } else if (i == 2) {
                    item.setName("Divorced");
                } else if (i == 3) {
                    item.setName("Windowed");
                } else if (i == 4) {
                    item.setName("Separated");
                } else if (i == 5) {
                    item.setName("Open relationship");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }


    public static ArrayList<WheelData> educationTypes() {
        try {

            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("High school");
                } else if (i == 1) {
                    item.setName("Some college");
                } else if (i == 2) {
                    item.setName("Associates degree");
                } else if (i == 3) {
                    item.setName("Bachelors degree");
                } else if (i == 4) {
                    item.setName("Graduate degree");
                } else if (i == 5) {
                    item.setName("PhD./Post doctoral");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }

        return list;
    }

    public static ArrayList<WheelData> ethnicitys() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Arabic/Middle eastern");
                } else if (i == 1) {
                    item.setName("Asian");
                } else if (i == 2) {
                    item.setName("Black/African descent");
                } else if (i == 3) {
                    item.setName("Caribbean");
                } else if (i == 4) {
                    item.setName("Caucasian/White");
                } else if (i == 5) {
                    item.setName("East Indian");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }


    public static ArrayList<WheelData> drinkings() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 4; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Non drinker");
                } else if (i == 1) {
                    item.setName("Social drinker");
                } else if (i == 2) {
                    item.setName("Heavy drinker");
                } else if (i == 3) {
                    item.setName("Trying to quit");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return list;
    }


    public static ArrayList<WheelData> smokings() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 4; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Non drinker");
                } else if (i == 1) {
                    item.setName("Light smoker");
                } else if (i == 2) {
                    item.setName("Heavy smoker");
                } else if (i == 3) {
                    item.setName("Trying to quit");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }


        return list;
    }


    public static ArrayList<WheelData> childs() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("No child");
                } else if (i == 1) {
                    item.setName("One child");
                } else if (i == 2) {
                    item.setName("Two children");
                } else if (i == 3) {
                    item.setName("Three children");
                } else if (i == 4) {
                    item.setName("Four children");
                } else if (i == 5) {
                    item.setName("Over four children");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }
        return list;
    }


    public static ArrayList<WheelData> getReason() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Scam/Spam");
                } else if (i == 1) {
                    item.setName("Fake/Offensive Message");
                } else if (i == 2) {
                    item.setName("Fake/Offensive Photo");
                } else if (i == 3) {
                    item.setName("Copyrighted problem");
                } else if (i == 4) {
                    item.setName("Underage");
                } else if (i == 5) {
                    item.setName("Other");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }

        return list;
    }

    public static ArrayList<WheelData> getSubject() {
        try {
            list = new ArrayList<>();
            WheelData item;
            for (int i = 0; i < 6; i++) {
                item = new WheelData();
                if (i == 0) {
                    item.setName("Profile/Photo");
                } else if (i == 1) {
                    item.setName("Sign in/Sign up" );
                } else if (i == 2) {
                    item.setName("Match/Message");
                } else if (i == 3) {
                    item.setName("Report a member");
                } else if (i == 4) {
                    item.setName("Technical Issues");
                } else if (i == 5) {
                    item.setName("Other");
                }
                list.add(item);
            }
        } catch (
                Exception e) {
            e.printStackTrace();
            CustomToast.makeText(MyApplication.getContext(), "sorry,data exeception....", R.drawable.ic_toast_warming).show();
        }

        return list;
    }
}
