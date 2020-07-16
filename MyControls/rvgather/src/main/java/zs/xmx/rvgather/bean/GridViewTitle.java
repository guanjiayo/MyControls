package zs.xmx.rvgather.bean;

/*
 * @创建者     默小铭
 * @博客       http://blog.csdn.net/u012792686
 * @本类描述
 * @内容说明
 *
 */
public class GridViewTitle {
    public final static int TITLE = 0x1111;
    public final static int CONTENT = 0x2222;
    private GridViewItem data;


    public int getType() {
        return type;
    }

    private int type;

    private int span;

    public int getSpan() {
        return span;
    }

    public void setSpan(int span) {
        this.span = span;
    }

    public GridViewItem getData() {
        return data;
    }

    public GridViewTitle(int type, GridViewItem data, int span) {
        this.type = type;
        this.data = data;
        this.span = span;
    }
}
