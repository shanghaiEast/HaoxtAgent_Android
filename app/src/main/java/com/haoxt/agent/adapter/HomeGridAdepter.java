package com.haoxt.agent.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.haoxt.agent.R;
import com.haoxt.agent.entity.HomeMenu;

import java.util.ArrayList;

import tft.mpos.library.util.StringUtil;


/**
 * Created by liuxx on 2019/10/21
 */

public class HomeGridAdepter extends BaseAdapter {
    private Context ctx;
    private ArrayList<HomeMenu> itemlist;

    public HomeGridAdepter(Activity context, ArrayList<HomeMenu> list) {
        this.ctx = context;
        this.itemlist = list;
    }

    @Override
    public int getCount() {
        return itemlist.size();
    }

    @Override
    public Object getItem(int i) {
        return itemlist.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHoder hoder;
        if (null == view) {
            view = LayoutInflater.from(ctx).inflate(R.layout.home_gridview_item, null);
            ImageView iv_icon = view.findViewById(R.id.iv_home_menu_ic);
            TextView tv_name = view.findViewById(R.id.tv_home_menu_name);
            hoder = new ViewHoder(iv_icon, tv_name);
            view.setTag(hoder);
        } else {
            hoder = (ViewHoder) view.getTag();
        }
        Drawable drawable = null;
        String str = "";
        switch (StringUtil.get(itemlist.get(i).getMenuAlias())) {
            case "PARTNER"://合作伙伴
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_partner);
                str = "合作伙伴";
                break;
            case "MERQUERY"://商户查询
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_mer_query);
                str = "商户查询";
                break;
            case "TERMINAL"://终端查询
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_ter);
                str = "终端查询";
                break;
            case "TRANSACTION"://交易管理
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_trans);
                str = "交易管理";
                break;
            case "TERPURCHASE"://机具采购
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_ter_chase);
                str = "机具采购";
                break;
            case "SETTLEMENT"://秒结管理
                drawable = ctx.getResources().getDrawable(R.drawable.home_menu_settlement);
                str = "秒结管理";
                break;
        }
        hoder.iv_icon.setImageDrawable(drawable);
        hoder.tv_name.setText(str);
        return view;
    }

    public static class ViewHoder {
        ImageView iv_icon;
        TextView tv_name;

        ViewHoder(ImageView iv, TextView tv) {
            this.iv_icon = iv;
            this.tv_name = tv;
        }

    }
}
