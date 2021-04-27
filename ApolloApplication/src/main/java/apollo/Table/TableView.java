package apollo.Table;

import apollo.Controller;
import apollo.Swing.PopupManager;

import java.awt.BorderLayout;
import java.io.IOException;


public class TableView {

	public static Boolean listviewMode = true;

    public static Boolean getListviewMode() {
        return listviewMode;
    }

    public static void setListviewMode(Boolean listviewMode) {
        TableView.listviewMode = listviewMode;
    }

    public static void listView() {
        Controller.getMainpanel().removeAll();
        Controller.setTopButtonPanel(Controller.getMainpanel());
        Controller.setFilter(Controller.getMainpanel());
        Controller.getMainpanel().add(Controller.getPane(), BorderLayout.PAGE_END);
        Controller.getMainFrame().setSize(1000,350);
        Controller.getMainFrame().setLocationRelativeTo(null);
    }

    public static void galleryView(){
        //Remove everything, then add back button panel and gallery view
        if (TableView.getListviewMode()) {
            TableView.setListviewMode(false);
            try {
                PopupManager.setGraphicPanel(Controller.getMainpanel(), Controller.getMainFrame(), Controller.getMainRushClass());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

    }
    
}
