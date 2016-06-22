package com.dev.fastrecyclerview.models;

import android.graphics.Bitmap;

import com.dev.fastrecyclerview.R;
import com.dev.fastrecyclerview.adapter.AdapterRecord;
import com.mikepenz.fastadapter.IDraggable;
import com.mikepenz.fastadapter.IExpandable;
import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.items.AbstractItem;

import java.util.List;

/**
 * Created by MyPC on 6/21/2016.
 */
public class RecordModel extends AbstractItem<RecordModel,AdapterRecord.ViewHolder> implements IExpandable<RecordModel, IItem>, IDraggable<RecordModel, IItem> {
    private int id;
    private String phoneNumber; // Thông tin quan trọng
    private long date; // Bạn bỏ qua , điền bừa
    private int duration; // Bạn bỏ qua , điền bừa
    private String path; // Bạn bỏ qua , điền bừa
    private int status; // Bạn bỏ qua , điền bừa
    private int Sync; // Bạn bỏ qua , điền bừa
    private String note; // Bạn bỏ qua , điền bừa
    private Bitmap avatar;

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSync() {
        return Sync;
    }

    public void setSync(int sync) {
        Sync = sync;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
//// TODO: 6/21/2016 here implement
    @Override
    public boolean isDraggable() {
        return false;
    }

    @Override
    public RecordModel withIsDraggable(boolean draggable) {
        return null;
    }

    @Override
    public boolean isExpanded() {
        return false;
    }

    @Override
    public RecordModel withIsExpanded(boolean collapsed) {
        return null;
    }

    @Override
    public RecordModel withSubItems(List<IItem> subItems) {
        return null;
    }

    @Override
    public List<IItem> getSubItems() {
        return null;
    }

    @Override
    public int getType() {
        return R.id.item;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_record;
    }


}
