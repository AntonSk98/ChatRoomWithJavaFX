package animation;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Animation {
    private TranslateTransition translateTransition;
    public Animation(Node node){
        translateTransition = new TranslateTransition(Duration.millis(100), node);
        translateTransition.setFromX(0f);
        translateTransition.setByX(10f);
        translateTransition.setCycleCount(3);
        translateTransition.setAutoReverse(true); // когда перетаскиваем поле необходимо чтобы оно возвращалось обратно

    }
    public void playAnimation(){
        translateTransition.playFromStart();
    }
}
