package com.haoxt.agent.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.entity.PartnerQueryListBean;

import java.util.ArrayList;

import tft.mpos.library.util.StringUtil;

/**
 * @Description: 合作伙伴查询adapter
 * @Author: liuxx
 * @CreateDate: 2019/10/23 11:07
 */
public class PartnerQueryListAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<PartnerQueryListBean> list;

    public PartnerQueryListAdapter(Activity context, ArrayList<PartnerQueryListBean> list) {
        this.ctx = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHoder hoder;

        if (null == view) {
            view = LayoutInflater.from(ctx).inflate(R.layout.partner_query_list_item, null);
            TextView tvCorporatename = view.findViewById(R.id.tv_corporatename);
            TextView tvLegalpersonname = view.findViewById(R.id.tv_legalpersonname);
            TextView tvPhonenum = view.findViewById(R.id.tv_phonenum);
            TextView tvFirstpartner = view.findViewById(R.id.tv_firstpartner);
            TextView tvSecondarypartner = view.findViewById(R.id.tv_secondarypartner);
            hoder = new ViewHoder(tvCorporatename, tvLegalpersonname, tvPhonenum, tvFirstpartner, tvSecondarypartner);
            view.setTag(hoder);
        } else {
            hoder = (ViewHoder) view.getTag();
        }

        if (!list.get(i).getCorporateName().isEmpty()) {
            hoder.tvCorporatename.setText(list.get(i).getCorporateName());
        }
        if (!list.get(i).getLegalPersonName().isEmpty()) {
            hoder.tvLegalpersonname.setText("法人姓名:"+list.get(i).getLegalPersonName());
        }
        if (!list.get(i).getPhoneNum().isEmpty()) {
            hoder.tvPhonenum.setText("联系方式:"+list.get(i).getPhoneNum());
        }
        if (!list.get(i).getFirstPartner().isEmpty()) {
            hoder.tvFirstpartner.setText("一级合作伙伴:"+list.get(i).getFirstPartner());
        }
        if (!"".equals(StringUtil.get(list.get(i).getSecondaryPartners()))) {
            hoder.tvSecondarypartner.setVisibility(View.VISIBLE);
            hoder.tvSecondarypartner.setText("二级合作伙伴:"+list.get(i).getSecondaryPartners());
        } else {
            hoder.tvSecondarypartner.setVisibility(View.GONE);
        }

        return view;
    }

    public static class ViewHoder {
        TextView tvCorporatename;
        TextView tvLegalpersonname;
        TextView tvPhonenum;
        TextView tvFirstpartner;
        TextView tvSecondarypartner;

        ViewHoder(TextView tvCorporatename, TextView tvLegalpersonname, TextView tvPhonenum, TextView tvFirstpartner, TextView tvSecondarypartner) {
            this.tvCorporatename = tvCorporatename;
            this.tvLegalpersonname = tvLegalpersonname;
            this.tvPhonenum = tvPhonenum;
            this.tvFirstpartner = tvFirstpartner;
            this.tvSecondarypartner = tvSecondarypartner;
        }

    }
}
