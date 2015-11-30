/*
 * 此代码创建于 2013-3-27 下午03:27:40。
 */
package com.apollo.demos.concurrent;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import com.apollo.base.util.BaseUtilities;
import com.apollo.base.util.IBaseConstants;
import com.apollo.swing.component.text.IntegerField;
import com.apollo.swing.laf.TLookAndFeelManager;

public class SwingWorkerDemo {

    public static void main(String[] args) {
        TLookAndFeelManager.loadAlloy(false);

        FindPrimeWindow window = new FindPrimeWindow();
        window.setVisible(true);
    }

}

/**
 * 查找素数窗口。
 */
@SuppressWarnings("all")
class FindPrimeWindow extends JFrame implements IBaseConstants, PropertyChangeListener {

    /**
     * 判断是否是素数。时间复杂度O(n)。
     * @param number 数字。
     * @return 标识是否是素数。
     */
    public static final boolean isPrime1(int number) {
        if (number < 2) {
            return false;
        }

        for (int i = 2; i < number; ++i) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是否是素数。改进，去掉偶数的判断，时间复杂度O(n/2)，速度提高一倍。
     * @param number 数字。
     * @return 标识是否是素数。
     */
    public static final Boolean isPrime2(int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        for (int i = 3; i < number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 判断是否是素数。进一步减少判断的范围，时间复杂度O(sqrt(n)/2)，速度提高O((n-sqrt(n))/2)。
     * <p>定理：如果n不是素数，则n有满足1<d<=sqrt(n)的一个因子d。</p>
     * <p>证明：如果n不是素数，则由定义n有一个因子d满足1<d<n。</p>
     * <p>如果d大于sqrt(n)，则n/d是满足1<n/d<=sqrt(n)的一个因子。</p>
     * <p>更多高效算法请查看http://www.cnblogs.com/luluping/archive/2010/03/03/1677552.html。</p>
     * @param number 数字。
     * @return 标识是否是素数。
     */
    public static final boolean isPrime3(int number) {
        if (number < 2) {
            return false;
        }

        if (number == 2) {
            return true;
        }

        for (int i = 3; i * i <= number; i += 2) {
            if (number % i == 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 查找素数的工作者。
     */
    protected class FindPrimeWorker extends SwingWorker<List<Integer>, Integer> {

        /**
         * 当前数字。
         */
        protected int m_currentNumber;

        /**
         * 总数。
         */
        protected int m_sum;

        /**
         * 计数。
         */
        protected int m_count = 1;

        /**
         * 构造方法。
         * @param startNumber 起始数字。
         * @param sum 总数。
         */
        public FindPrimeWorker(int startNumber, int sum) {
            m_currentNumber = startNumber;
            m_sum = sum;
            addPropertyChangeListener(FindPrimeWindow.this);
        }

        /**
         * @see javax.swing.SwingWorker#doInBackground()
         */
        @Override
        protected List<Integer> doInBackground() throws Exception {
            List<Integer> primes = new ArrayList<Integer>();

            while (!isCancelled() && primes.size() < m_sum) {
                while (!isPrime1(m_currentNumber++)) {
                }

                int prime = m_currentNumber - 1;
                primes.add(prime);

                publish(prime);
                setProgress(100 * primes.size() / m_sum);
            }

            return primes;
        }

        /**
         * @see javax.swing.SwingWorker#process(java.util.List)
         */
        @Override
        protected void process(List<Integer> chunks) {
            if (!isCancelled()) {
                for (Integer chunk : chunks) {
                    m_result.append(chunk + (m_count++ % 14 == 0 ? "\n" : "\t"));
                }
            }
        }

        /**
         * @see javax.swing.SwingWorker#done()
         */
        @Override
        protected void done() {
            List<Integer> primes = null;

            try {
                if (!isCancelled()) {
                    primes = get();
                }

            } catch (InterruptedException ex) {
                ex.printStackTrace();

            } catch (ExecutionException ex) {
                ex.printStackTrace();
            }

            m_result.append(isCancelled() ? "已取消！" : "已查找完毕，共查找 " + primes.size() + " 个素数！");

            removePropertyChangeListener(FindPrimeWindow.this);
            m_findPrimeWorker = null;
            constrain();
        }

    }

    /**
     * 输入框------起始数字。
     */
    protected IntegerField m_startNumber = new IntegerField(4, 0, 999999, 0);

    /**
     * 输入框------总数。
     */
    protected IntegerField m_sum = new IntegerField(4, 0, 999999, 10000);

    /**
     * 显示区域------结果。
     */
    protected JTextArea m_result = new JTextArea();

    /**
     * 进度条------查找进度。
     */
    protected JProgressBar m_progress = new JProgressBar(0, 100);

    /**
     * 动作------开始。
     */
    protected Action m_start = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_START);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            start();
        }

    };

    /**
     * 动作------取消。
     */
    protected Action m_cancel = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_CANCEL);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            cancel();
        }

    };

    /**
     * 动作------关闭。
     */
    protected Action m_close = new AbstractAction() {

        {
            BaseUtilities.setAction(this, I18N_INFO_CLOSE);
        }

        /**
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        public void actionPerformed(ActionEvent evt) {
            close();
        }

    };

    /**
     * 查找素数的工作者。
     */
    protected FindPrimeWorker m_findPrimeWorker = null;

    /**
     * 构造方法。
     */
    public FindPrimeWindow() {
        super("查找素数");

        m_result.setEditable(false);
        JScrollPane resultView = new JScrollPane(m_result);
        resultView.setBorder(BorderFactory.createTitledBorder("查找结果"));

        m_progress.setStringPainted(true);
        m_progress.setBorder(BorderFactory.createTitledBorder("查找进度"));

        m_startNumber.setMinimumSize(m_startNumber.getPreferredSize());
        m_sum.setMinimumSize(m_sum.getPreferredSize());

        JPanel view = new JPanel(new GridBagLayout());
        BaseUtilities.addComponentWithBothFill(view, resultView, 0, 0, GBC_REMAINDER, 1, GBC_WEST);
        BaseUtilities.addComponentWithHorizontalFill(view, m_progress, 0, 1, GBC_REMAINDER, 1, GBC_WEST);
        BaseUtilities.addComponentWithNoSpace(view, new JLabel(), 0, 2, 1, 1, 1.0, 0.0, GBC_WEST, GBC_HORIZONTAL);
        BaseUtilities.addComponentWithNoneFill(view, new JLabel("起始数字："), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, m_startNumber, GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JLabel("查找总数："), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, m_sum, GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JButton(m_start), GBC_RELATIVE, 2, GBC_EAST);
        BaseUtilities.addComponentWithNoneFill(view, new JButton(m_cancel), GBC_RELATIVE, 2, GBC_EAST);
        JButton closeBtn = new JButton(m_close);
        BaseUtilities.addComponentWithNoneFill(view, closeBtn, GBC_RELATIVE, 2, GBC_EAST);

        setContentPane(view);
        rootPane.setDefaultButton(closeBtn);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setPreferredSize(new Dimension(800, 600));
        setMinimumSize(new Dimension(800, 600));

        pack();
        BaseUtilities.center(this);

        constrain();
    }

    /**
     * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress".equals(evt.getPropertyName())) {
            m_progress.setValue((Integer) evt.getNewValue());
        }
    }

    /**
     * @see javax.swing.JFrame#processWindowEvent(java.awt.event.WindowEvent)
     */
    protected void processWindowEvent(WindowEvent evt) {
        if (evt.getID() == WindowEvent.WINDOW_CLOSING) {
            close();
        }

        super.processWindowEvent(evt);
    }

    /**
     * 约束。
     */
    protected void constrain() {
        boolean hasFindPrimeWorker = m_findPrimeWorker != null;

        m_startNumber.setEnabled(!hasFindPrimeWorker);
        m_sum.setEnabled(!hasFindPrimeWorker);
        m_start.setEnabled(!hasFindPrimeWorker);
        m_cancel.setEnabled(hasFindPrimeWorker);
    }

    /**
     * 开始。
     */
    protected void start() {
        if (m_findPrimeWorker != null) {
            m_findPrimeWorker.cancel(true);
        }

        try {
            m_startNumber.commitEdit();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            m_startNumber.requestFocusInWindow();
            return;
        }

        try {
            m_sum.commitEdit();

        } catch (ParseException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
            m_sum.requestFocusInWindow();
            return;
        }

        m_result.setText("");
        m_result.append("开始查找：\n");

        m_findPrimeWorker = new FindPrimeWorker(m_startNumber.getInteger().intValue(), m_sum.getInteger().intValue());
        constrain();

        m_findPrimeWorker.execute();
    }

    /**
     * 取消。
     */
    protected void cancel() {
        m_findPrimeWorker.cancel(true);
    }

    /**
     * 关闭。
     */
    protected void close() {
        if (m_findPrimeWorker != null) {
            m_findPrimeWorker.cancel(true);
        }

        System.exit(0);
    }

}
