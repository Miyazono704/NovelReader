package com.example.NovelReader.ai;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.NovelReader.MyApplication;
import com.example.NovelReader.R;
import com.example.NovelReader.javabean.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * AI智能推荐页面
 */
public class AIRecommendActivity extends AppCompatActivity {

    private EditText etPreference;
    private Button btnRecommend;
    private Button btnRecommendByHistory;
    private ProgressBar progressBar;
    private TextView tvAiMessage;
    private RecyclerView rvRecommendations;
    private LinearLayout llEmpty;
    private ImageView ivBack;

    private AIRecommendService aiService;
    private RecommendAdapter adapter;
    private List<AIRecommendService.AIRecommendBook> recommendList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ai_recommend);

        initViews();
        initService();
        initListeners();
    }

    private void initViews() {
        etPreference = findViewById(R.id.et_preference);
        btnRecommend = findViewById(R.id.btn_recommend);
        btnRecommendByHistory = findViewById(R.id.btn_recommend_by_history);
        progressBar = findViewById(R.id.progress_bar);
        tvAiMessage = findViewById(R.id.tv_ai_message);
        rvRecommendations = findViewById(R.id.rv_recommendations);
        llEmpty = findViewById(R.id.ll_empty);
        ivBack = findViewById(R.id.iv_back);

        recommendList = new ArrayList<>();
        adapter = new RecommendAdapter(recommendList);
        rvRecommendations.setLayoutManager(new LinearLayoutManager(this));
        rvRecommendations.setAdapter(adapter);
    }

    private void initService() {
        aiService = new AIRecommendService();
    }

    private void initListeners() {
        // 返回按钮
        ivBack.setOnClickListener(v -> finish());

        // 根据偏好推荐
        btnRecommend.setOnClickListener(v -> {
            String preference = etPreference.getText().toString().trim();
            if (preference.isEmpty()) {
                Toast.makeText(this, "请输入您的阅读偏好", Toast.LENGTH_SHORT).show();
                return;
            }
            showLoading(true);
            aiService.getRecommendationsByPreference(preference, new AIRecommendService.AIRecommendCallback() {
                @Override
                public void onSuccess(List<AIRecommendService.AIRecommendBook> recommendations, String aiMessage) {
                    showLoading(false);
                    showRecommendations(recommendations, aiMessage);
                }

                @Override
                public void onError(String errorMessage) {
                    showLoading(false);
                    showError(errorMessage);
                }
            });
        });

        // 根据阅读历史推荐
        btnRecommendByHistory.setOnClickListener(v -> {
            showLoading(true);
            // 获取用户书架数据
            List<Book> userBooks = getUserBooks();
            aiService.getRecommendations(userBooks, new AIRecommendService.AIRecommendCallback() {
                @Override
                public void onSuccess(List<AIRecommendService.AIRecommendBook> recommendations, String aiMessage) {
                    showLoading(false);
                    showRecommendations(recommendations, aiMessage);
                }

                @Override
                public void onError(String errorMessage) {
                    showLoading(false);
                    showError(errorMessage);
                }
            });
        });
    }

    /**
     * 获取用户书架数据
     */
    private List<Book> getUserBooks() {
        // 从MyApplication获取用户书架数据
        List<Book> cartBooks = MyApplication.getCartBooks();
        if (cartBooks != null) {
            return cartBooks;
        }
        return new ArrayList<>();
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnRecommend.setEnabled(!show);
        btnRecommendByHistory.setEnabled(!show);
        if (show) {
            llEmpty.setVisibility(View.GONE);
            tvAiMessage.setVisibility(View.GONE);
            rvRecommendations.setVisibility(View.GONE);
        }
    }

    private void showRecommendations(List<AIRecommendService.AIRecommendBook> recommendations, String message) {
        if (recommendations == null || recommendations.isEmpty()) {
            llEmpty.setVisibility(View.VISIBLE);
            tvAiMessage.setVisibility(View.GONE);
            rvRecommendations.setVisibility(View.GONE);
        } else {
            llEmpty.setVisibility(View.GONE);
            tvAiMessage.setVisibility(View.VISIBLE);
            tvAiMessage.setText(message);
            rvRecommendations.setVisibility(View.VISIBLE);
            
            recommendList.clear();
            recommendList.addAll(recommendations);
            adapter.notifyDataSetChanged();
        }
    }

    private void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
        llEmpty.setVisibility(View.VISIBLE);
        tvAiMessage.setVisibility(View.GONE);
        rvRecommendations.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aiService != null) {
            aiService.shutdown();
        }
    }

    /**
     * 推荐列表适配器
     */
    private class RecommendAdapter extends RecyclerView.Adapter<RecommendAdapter.ViewHolder> {

        private final List<AIRecommendService.AIRecommendBook> data;

        public RecommendAdapter(List<AIRecommendService.AIRecommendBook> data) {
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_ai_recommend, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            AIRecommendService.AIRecommendBook book = data.get(position);
            holder.tvName.setText(book.getName());
            holder.tvAuthor.setText("作者：" + book.getAuthor());
            holder.tvCategory.setText(book.getCategory());
            holder.tvReason.setText("推荐理由：" + book.getReason());
            holder.tvDescription.setText(book.getDescription());
            holder.tvIndex.setText(String.valueOf(position + 1));
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvName, tvAuthor, tvCategory, tvReason, tvDescription, tvIndex;
            CardView cardView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvName = itemView.findViewById(R.id.tv_book_name);
                tvAuthor = itemView.findViewById(R.id.tv_book_author);
                tvCategory = itemView.findViewById(R.id.tv_book_category);
                tvReason = itemView.findViewById(R.id.tv_book_reason);
                tvDescription = itemView.findViewById(R.id.tv_book_description);
                tvIndex = itemView.findViewById(R.id.tv_index);
                cardView = itemView.findViewById(R.id.card_view);
            }
        }
    }
}
