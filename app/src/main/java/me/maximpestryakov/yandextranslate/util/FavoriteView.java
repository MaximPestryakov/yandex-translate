package me.maximpestryakov.yandextranslate.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import me.maximpestryakov.yandextranslate.R;

public class FavoriteView extends AppCompatImageView {

    private boolean checked;

    private OnClickListener l;

    public FavoriteView(Context context) {
        super(context);
        init(context);
    }

    public FavoriteView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FavoriteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        setClickable(true);
        int[] attrs;
        attrs = new int[]{R.attr.selectableItemBackground};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        Drawable drawable = typedArray.getDrawable(0);
        typedArray.recycle();
        setBackground(drawable);
        super.setOnClickListener(v -> {
            setChecked(!checked);
            l.onClick(this);
        });
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            setImageResource(R.drawable.ic_bookmark_black_24dp);
        } else {
            setImageResource(R.drawable.ic_bookmark_border_black_24dp);
        }
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.l = l;
    }
}
