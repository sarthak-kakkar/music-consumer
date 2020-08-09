package learning.kafka.musicconsumer.ui;

import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.shared.data.sort.SortDirection;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import javax.servlet.annotation.WebServlet;
import learning.kafka.musicconsumer.service.ArtistResult;
import learning.kafka.musicconsumer.service.ResultListener;
import org.springframework.beans.factory.annotation.Autowired;

@SpringUI
public final class VaadinConsumer extends UI {

  private final Grid<ArtistResult> grid = new Grid<>(ArtistResult.class);
  private final ResultListener resultListener;

  @Autowired
  public VaadinConsumer(ResultListener resultListener) {
    this.resultListener = resultListener;
  }

  @Override
  protected void init(VaadinRequest request) {
    final VerticalLayout verticalLayout = new VerticalLayout();
    final HorizontalLayout horizontalLayout = new HorizontalLayout();

    grid.setSizeFull();
    grid.sort("listenersCount", SortDirection.DESCENDING);
    grid.getColumn("songs").setExpandRatio(1);
    grid.getColumn("artist").setMinimumWidth(150);
    grid.getColumn("listenersCount").setMinimumWidth(150);

    final Button cta = new Button("Run!");
    final Label label = new Label();
    loadTable(cta, label);

    horizontalLayout.addComponent(cta);
    verticalLayout.addComponent(horizontalLayout);
    grid.recalculateColumnWidths();
    verticalLayout.addComponent(grid);
    verticalLayout.addComponent(label);

    setContent(verticalLayout);
  }

  private void loadTable(Button cta, Label label) {
    cta.addClickListener(e -> {
      grid.asSingleSelect().clear();
      grid.setItems(resultListener.getArtistResultMap().values());
      label.setValue("Size -> " + resultListener.getArtistResultMap().size());
    });
  }

  @WebServlet(urlPatterns = "/*", name = "VaadinConsumerServlet", asyncSupported = true)
  @VaadinServletConfiguration(ui = VaadinConsumer.class, productionMode = false)
  public static class VaadinConsumerServlet extends VaadinServlet { }
}