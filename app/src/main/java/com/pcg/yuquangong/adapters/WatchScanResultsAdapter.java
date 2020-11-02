package com.pcg.yuquangong.adapters;

import android.bluetooth.BluetoothDevice;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.crrepa.ble.scan.bean.CRPScanDevice;
import com.pcg.yuquangong.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WatchScanResultsAdapter extends RecyclerView.Adapter<WatchScanResultsAdapter.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvItemWatchName)
        TextView tvItemWatchName;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public interface OnAdapterItemClickListener {

        void onAdapterViewClick(View view);
    }

    private static final Comparator<CRPScanDevice> SORTING_COMPARATOR = new Comparator<CRPScanDevice>() {
        @Override
        public int compare(CRPScanDevice scanDevice, CRPScanDevice t1) {
            return scanDevice.getDevice().getAddress().compareTo(t1.getDevice().getAddress());
        }
    };

    private final List<CRPScanDevice> data = new ArrayList<>();
    private OnAdapterItemClickListener onAdapterItemClickListener;
    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (onAdapterItemClickListener != null) {
                onAdapterItemClickListener.onAdapterViewClick(v);
            }

        }
    };

    public void addScanResult(CRPScanDevice bleScanResult) {
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).getDevice().equals(bleScanResult.getDevice())) {
                data.set(i, bleScanResult);
                notifyItemChanged(i);
                return;
            }
        }

        data.add(bleScanResult);
        Collections.sort(data, SORTING_COMPARATOR);
        notifyDataSetChanged();
    }

    public void clearScanResults() {
        data.clear();
        notifyDataSetChanged();
    }

    public CRPScanDevice getItemAtPosition(int childAdapterPosition) {
        return data.get(childAdapterPosition);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CRPScanDevice scanDevice = data.get(position);
        final BluetoothDevice bleDevice = scanDevice.getDevice();
//        holder.tvItemWatchName.setText(String.format("%s (%s) RSSI: %d", bleDevice.getAddress(), bleDevice.getName(), scanDevice.getRssi()));
        holder.tvItemWatchName.setText(bleDevice.getName());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_watch_unconnect, parent, false);
        itemView.setOnClickListener(onClickListener);
        return new ViewHolder(itemView);
    }

    public void setOnAdapterItemClickListener(OnAdapterItemClickListener onAdapterItemClickListener) {
        this.onAdapterItemClickListener = onAdapterItemClickListener;
    }

}
