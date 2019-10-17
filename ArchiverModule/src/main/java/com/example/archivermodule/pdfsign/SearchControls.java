package com.example.archivermodule.pdfsign;


import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;


import com.example.archivermodule.R;
import com.kinggrid.iapppdf.ui.viewer.IAppPDFActivity;


public class SearchControls extends LinearLayout implements OnClickListener,OnEditorActionListener {

    
    private EditText m_edit;
    private ImageButton m_prevButton;
    private ImageButton m_nextButton;
    private ImageButton m_closeButton;
    private IAppPDFActivity parent;
    private boolean firstLoad = true;

    public SearchControls(final IAppPDFActivity parent) {
        super(parent);
        setVisibility(View.GONE);
        setOrientation(LinearLayout.VERTICAL);
        this.parent = parent;

        LayoutInflater.from(parent).inflate(R.layout.seach_controls, this, true);
        m_closeButton = (ImageButton) findViewById(R.id.search_controls_close);
        m_prevButton = (ImageButton) findViewById(R.id.search_controls_prev);
        m_nextButton = (ImageButton) findViewById(R.id.search_controls_next);
        m_edit = (EditText) findViewById(R.id.search_controls_edit);

        m_prevButton.setOnClickListener(this);
        m_nextButton.setOnClickListener(this);
        m_edit.setOnEditorActionListener(this);
        
        m_closeButton.setOnClickListener(this);
        
    }

    public void show(){
    	if(firstLoad){
    		parent.showViewToPDF(this);
    		firstLoad = false;
    	}
    	this.setVisibility(View.VISIBLE);
    	 m_edit.requestFocus();
    }
    
    public void dismiss(){
    	this.setVisibility(View.GONE);
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event) {
    	
        return false;
    }

    public int getActualHeight() {
        return m_edit.getHeight();
    }

	@Override
	public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.search_controls_close) {
            parent.stopSearchText();
            setVisibility(View.GONE);

        } else if (i == R.id.search_controls_prev) {
            parent.searchText(m_edit.getText().toString(), false, getActualHeight());

        } else if (i == R.id.search_controls_next) {
            parent.searchText(m_edit.getText().toString(), true, getActualHeight());


        }
	}

	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		
		return false;
		
	}
}
