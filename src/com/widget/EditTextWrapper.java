package com.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView.OnEditorActionListener;
import com.example.android_edittext_withdelete.R;


/**
 * EditText的封装控件，右侧多了一个“清空”的圆型按钮。
 * @author Jack  
 * @version 创建时间：2013-12-18  下午7:53:29
 */
public class EditTextWrapper extends LinearLayout implements OnFocusChangeListener {
    
    private OnFocusChangeListener mFocusListener;
    protected EditText mEditText;
    /**删除按钮*/
    private ImageView mClear;
    private TextWatcher onEditTextChangedListener;
    
    
    public EditTextWrapper(Context context) {
        this(context, null);
    }
    
    public EditTextWrapper(Context context, AttributeSet attrs) {
        super(context, attrs);
        initResource();
    }
    
    /**
     * 初始化资源
     */
    private void initResource() {
    	
    	LayoutInflater.from(getContext()).inflate(R.layout.layout_edit_text_wrapper, this);
        mEditText = (EditText) findViewById(R.id.layout_edit_text);
        mClear = (ImageView) findViewById(R.id.layout_edit_text_clear);
        
        mEditText.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	if(onEditTextChangedListener != null) {
            		onEditTextChangedListener.onTextChanged(s, start, before, count);
            	}
            	
                if (s != null && s.length() > 0) {
                    mClear.setVisibility(VISIBLE);
                } else {
                    mClear.setVisibility(INVISIBLE);
                }
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        
        mClear.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mEditText.setText("");
            }
        });
    }
    
    public void setTextChangedListener(TextWatcher mListener) {
    	this.onEditTextChangedListener = mListener;
    }
    
    public EditText unwrap() {
        return mEditText;
    }
    
    public Editable getText() {
        return mEditText.getText();
    }
    
    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener listener) {
        mFocusListener = listener;
    }
    
    public void setOnEditorActionListener(OnEditorActionListener listener) {
        mEditText.setOnEditorActionListener(listener);
    }
    
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
    	
        if (hasFocus && mEditText.getText().toString().length() > 0) {  //若有文本就显示清空按钮
        	
            mClear.setVisibility(VISIBLE);
        } else {  //若没有文本就隐藏清空按钮
        	
            mClear.setVisibility(INVISIBLE);
        }
        if (null != mFocusListener) {
        	
            mFocusListener.onFocusChange(v, hasFocus);
        }
    }

	/**
	 * 设置字体的颜色
	 * @param color
	 */
	public void setTextColor(int color){
		
		mEditText.setTextColor(color);
	}
}
