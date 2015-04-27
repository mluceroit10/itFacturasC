package common;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;

class BasicLinkButtonUI extends MetalButtonUI {
	private static final BasicLinkButtonUI ui = new BasicLinkButtonUI();
	
	public BasicLinkButtonUI() {
	}
	
	public static ComponentUI createUI(JComponent jcomponent) {
		return ui;
	}
	
	protected void paintText(Graphics g, JComponent com, Rectangle rect, String s) {
		JLinkButton bn = (JLinkButton) com;
		ButtonModel bnModel = bn.getModel();
		if (bnModel.isEnabled()) {
			if (bnModel.isPressed())
				bn.setForeground(bn.getActiveLinkColor());
			else if (bn.isLinkVisited())
				bn.setForeground(bn.getVisitedLinkColor());
			
			else
				bn.setForeground(bn.getLinkColor());
		} else {
			if (bn.getDisabledLinkColor() != null)
				bn.setForeground(bn.getDisabledLinkColor());
		}
		super.paintText(g, com, rect, s);
		int behaviour = bn.getLinkBehavior();
		boolean drawLine = false;
		if (behaviour == JLinkButton.HOVER_UNDERLINE) {
			if (bnModel.isRollover())
				drawLine = true;
		} else if (behaviour == JLinkButton.ALWAYS_UNDERLINE || behaviour == JLinkButton.SYSTEM_DEFAULT)
			drawLine = true;
		if (!drawLine)
			return;
		FontMetrics fm = g.getFontMetrics();
		int x = rect.x + getTextShiftOffset();
		int y = (rect.y + fm.getAscent() + fm.getDescent() + getTextShiftOffset()) - 1;
		if (bnModel.isEnabled()) {
			g.setColor(bn.getForeground());
			g.drawLine(x, y, (x + rect.width) - 1, y);
		} else {
			g.setColor(bn.getBackground().brighter());
			g.drawLine(x, y, (x + rect.width) - 1, y);
		}
	}
	
}