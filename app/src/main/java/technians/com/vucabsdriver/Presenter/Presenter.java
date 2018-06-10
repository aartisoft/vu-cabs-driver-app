package technians.com.vucabsdriver.Presenter;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}
