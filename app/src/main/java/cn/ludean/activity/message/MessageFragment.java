package cn.ludean.activity.message;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.ludean.R;
import cn.ludean.SharedPrefrenceUtils;
import cn.ludean.activity.MessageDetailsActivity;
import cn.ludean.adapter.GongGaoRecycerAdapter;
import cn.ludean.adapter.MessageRecycerAdapter;
import cn.ludean.beans.MessageBeans;
import cn.ludean.net.NetCallBack;
import cn.ludean.net.NetConstant;
import cn.ludean.net.RetrofitUtils;
import io.reactivex.disposables.Disposable;

/**
 * 公告 消息列表
 */
public class MessageFragment extends Fragment {

    @BindView(R.id.recycler_message)
    RecyclerView recyclerMessage;
    private Unbinder unbinder;
    private Map<String, String> map;
    private int type;

    public MessageFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        unbinder = ButterKnife.bind(this, view);
        type = getArguments().getInt("type");
        map = new HashMap<>();
        if (type == 0) {
            map.put("functionType", "1");
            initRecycler(NetConstant.noticePushInfo, map);
        } else {
            map.put("functionType", "");
            initRecycler(NetConstant.hisPushInfo, map);
        }

        return view;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (getActivity() != null && recyclerMessage != null && !hidden) {
            if (type == 0) {
                map.put("functionType", "1");
                initRecycler(NetConstant.noticePushInfo, map);
            } else {
                map.put("functionType", "");
                initRecycler(NetConstant.hisPushInfo, map);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null && recyclerMessage != null) {
            if (type == 0) {
                map.put("functionType", "1");
                initRecycler(NetConstant.noticePushInfo, map);
            } else {
                map.put("functionType", "");
                initRecycler(NetConstant.hisPushInfo, map);
            }
        }
    }

    private void initRecycler(String url, Map map) {
        RetrofitUtils.getInstance().postHttp(url, SharedPrefrenceUtils.getString(getActivity(), "uuid"), map, new NetCallBack<MessageBeans>() {
            @Override
            public void star(Disposable d) {
                Log.e("Disposable", d.toString());
            }

            @Override
            public void onSuccess(Object o) {
                if (o instanceof MessageBeans) {
                    MessageBeans messageBeans = (MessageBeans) o;
                    Log.e("messageBeans", messageBeans.toString());
                    if (messageBeans.getData() != null) {
                        ArrayList<MessageBeans.DataBean> dataBeans = new ArrayList<>();
                        if (type != 0) {
                            for (int i = 0; i < messageBeans.getData().size(); i++) {
                                if (!messageBeans.getData().get(i).getFunctionType().equals("1")) {
                                    dataBeans.add(messageBeans.getData().get(i));
                                }
                            }
                        } else {
                            dataBeans.addAll(messageBeans.getData());
                        }
                        if (type == 0) {
                            GongGaoRecycerAdapter adapter = new GongGaoRecycerAdapter(R.layout.item_gonggao_recycler, dataBeans);
                            recyclerMessage.setAdapter(adapter);
                            recyclerMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
                        } else {
                            MessageRecycerAdapter adapter = new MessageRecycerAdapter(R.layout.item_message_recycler, dataBeans);
                            recyclerMessage.setAdapter(adapter);
                            recyclerMessage.setLayoutManager(new LinearLayoutManager(getActivity()));
                            adapter.setOnItemClickListener((adapter1, view, position) -> {
                                Intent intent = new Intent(getActivity(), MessageDetailsActivity.class);
                                intent.putExtra("id", dataBeans.get(position).getId());
                                intent.putExtra("extras", dataBeans.get(position).getExtras());
                                intent.putExtra("functionDataId", dataBeans.get(position).getFunctionDataId());
                                intent.putExtra("functionType", dataBeans.get(position).getFunctionType());
                                startActivity(intent);
                            });
                        }

                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                Log.e("error", Objects.requireNonNull(throwable.getMessage()));
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null)
            unbinder.unbind();
    }
}
