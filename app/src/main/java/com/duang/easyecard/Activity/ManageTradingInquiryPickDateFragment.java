package com.duang.easyecard.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duang.easyecard.R;
import com.duang.easyecard.Util.TradingInquiryDateUtil;
import com.rey.material.app.DatePickerDialog;
import com.rey.material.app.Dialog;
import com.rey.material.app.DialogFragment;
import com.rey.material.app.ThemeManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import br.com.dina.ui.model.ViewItem;
import br.com.dina.ui.widget.UITableView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ManageTradingInquiryPickDateFragment.OnQureyButtonClickListener} interface
 * to handle interaction events.
 */
public class ManageTradingInquiryPickDateFragment extends Fragment {

    private View viewFragment;
    private UITableView setStartTimeTableView;
    private UITableView setEndTimeTableView;

    private OnQureyButtonClickListener mCallbackListener;

    public ManageTradingInquiryPickDateFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (viewFragment == null) {
            viewFragment = inflater.inflate(R.layout.fragment_manage_trading_inquiry_pick_date,
                    container, false);
        }
        return viewFragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        // 实例化控件
        setStartTimeTableView = (UITableView) getActivity().findViewById(
                R.id.manage_trading_inquiry_set_start_time);
        setEndTimeTableView = (UITableView) getActivity().findViewById(
                R.id.manage_trading_inquiry_set_end_time);
        initTimeTableView();
        setStartTimeTableView.setClickListener(new UITableView.ClickListener() {
            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            @Override
            public void onClick(int i) {
                Dialog.Builder builder = new DatePickerDialog.Builder(isLightTheme ?
                        R.style.Material_App_Dialog_DatePicker_Light :
                        R.style.Material_App_Dialog_DatePicker) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        Toast.makeText(getActivity(), "Date is " + date, Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                }.date(20, 11, 2015);
                builder.positiveAction(getString(R.string.OK))
                        .negativeAction(getString(R.string.Cancel));
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
            }
        });
        setEndTimeTableView.setClickListener(new UITableView.ClickListener() {
            boolean isLightTheme = ThemeManager.getInstance().getCurrentTheme() == 0;
            @Override
            public void onClick(int i) {
                Dialog.Builder builder = new DatePickerDialog.Builder(isLightTheme ?
                        R.style.Material_App_Dialog_DatePicker_Light :
                        R.style.Material_App_Dialog_DatePicker) {
                    @Override
                    public void onPositiveActionClicked(DialogFragment fragment) {
                        DatePickerDialog dialog = (DatePickerDialog) fragment.getDialog();
                        String date = dialog.getFormattedDate(SimpleDateFormat.getDateInstance());
                        Toast.makeText(getActivity(), "Date is " + date, Toast.LENGTH_SHORT).show();
                        super.onPositiveActionClicked(fragment);
                    }

                    @Override
                    public void onNegativeActionClicked(DialogFragment fragment) {
                        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
                        super.onNegativeActionClicked(fragment);
                    }
                }.date(20, 11, 2015);
                builder.positiveAction(getString(R.string.OK))
                        .negativeAction(getString(R.string.Cancel));
                DialogFragment fragment = DialogFragment.newInstance(builder);
                fragment.show(getFragmentManager(), null);
            }
        });
    }
    // 初始化时间选择列表
    private void initTimeTableView() {
        TradingInquiryDateUtil myDateUtil = new TradingInquiryDateUtil(this.getActivity());
        // 显示到UITableView
        generateTableItem(setStartTimeTableView, getString(R.string.start_time),
                myDateUtil.getHistoryStartDate(),
                myDateUtil.getHistoryStartDayOfWeek());
        generateTableItem(setEndTimeTableView, getString(R.string.end_time),
                myDateUtil.getHistoryEndDate(),
                myDateUtil.getHistoryEndDayOfWeek());
    }

    // 构造UItableView的列表项，传入title和content
    private void generateTableItem(UITableView tableView, String title, String date, String day) {
        LayoutInflater mLayoutInflater = (LayoutInflater) getActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout linearLayout = (LinearLayout) mLayoutInflater.inflate(
                R.layout.table_view_custom_item_2, null);
        TextView titleText = (TextView) linearLayout.getChildAt(0);
        titleText.setText(title);
        TextView dateText = (TextView) linearLayout.getChildAt(1);
        dateText.setText(date);
        TextView dayText = (TextView) linearLayout.getChildAt(2);
        dayText.setText(day);
        ViewItem v = new ViewItem(linearLayout);
        v.setClickable(true);
        tableView.addViewItem(v);
        tableView.commit();
    }
    // 更新UITableView的列表项
    private void updateTableItem(UITableView tableView, String title, String date, String day) {
        tableView.clear();
        generateTableItem(tableView, title, date, day);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnQureyButtonClickListener) {
            mCallbackListener = (OnQureyButtonClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbackListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     */
    public interface OnQureyButtonClickListener {
        void onQueryBtnClick(View v);
    }
}
