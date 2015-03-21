/*******************************************************************************
 * Copyright (c) 2010-2014, Marton Bur, Akos Horvath, Zoltan Ujhelyi, Istvan Rath and Daniel Varro
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Marton Bur - initial API and implementation
 *******************************************************************************/
package org.eclipse.incquery.tooling.localsearch.ui.debugger.views;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gef4.layout.LayoutAlgorithm;
import org.eclipse.gef4.layout.algorithms.TreeLayoutAlgorithm;
import org.eclipse.gef4.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.gef4.zest.core.viewers.GraphViewer;
import org.eclipse.gef4.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.gef4.zest.core.viewers.ZoomContributionViewItem;
import org.eclipse.gef4.zest.core.widgets.ZestStyles;
import org.eclipse.incquery.runtime.localsearch.MatchingFrame;
import org.eclipse.incquery.runtime.localsearch.operations.ISearchOperation;
import org.eclipse.incquery.runtime.localsearch.plan.SearchPlanExecutor;
import org.eclipse.incquery.tooling.localsearch.ui.debugger.provider.OperationListContentProvider;
import org.eclipse.incquery.tooling.localsearch.ui.debugger.provider.OperationListLabelProvider;
import org.eclipse.incquery.tooling.localsearch.ui.debugger.provider.ZestNodeContentProvider;
import org.eclipse.incquery.tooling.localsearch.ui.debugger.views.internal.BreakPointListener;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 
 * @author Marton Bur
 *
 */
public class LocalSearchDebugView extends ViewPart implements IZoomableWorkbenchPart {


    private final class ColumnLabelProviderExtension extends ColumnLabelProvider {
		private int columnIndex;

		public ColumnLabelProviderExtension(int i) {
			this.columnIndex = i;
		}

		@Override
		public String getText(Object inputElement) {
			
			MatchingFrame frame = (MatchingFrame)inputElement;
			Object element = frame.get(columnIndex);
			
			if(element == null){
				return "null";
			}
			if(element instanceof EObject){
				EObject eObject = ((EObject) element);
				
				EStructuralFeature feature = eObject.eClass().getEStructuralFeature("name");
				if(feature != null){
					if(!feature.isMany()){
						return eObject.eGet(feature).toString();
					}							
				} else {
					feature = eObject.eClass().getEStructuralFeature(0);
					if(!feature.isMany()){
						return eObject.eGet(feature).toString();
					}														
				}
			}
			return element.toString();
		}
	}

	public static final String ID = "org.eclipse.incquery.tooling.localsearch.ui.LocalSearchDebugView";

	public static final String VIEWER_KEY = "key";
    
    private OperationListContentProvider operationListContentProvider;
    private TreeViewer operationListViewer;
    private OperationListLabelProvider operationListLabelProvider;

    private GraphViewer graphViewer;
    private ZestNodeContentProvider zestContentProvider;
    
    private List<Object> breakpoints = Lists.newLinkedList();
    
    private boolean halted = true;

	private SashForm planSashForm;

	private CTabFolder matchesTabFolder;

	private Map<String, TableViewer> matchViewersMap = Maps.newHashMap();;


	public LocalSearchDebugView() {
    }
    
    public List<Object> getBreakpoints() {
        return breakpoints;
    }
    
    public CTabFolder getMatchesTabFolder() {
    	return matchesTabFolder;
    }
    
    public boolean isBreakpointHit(SearchPlanExecutor planExecutor) {
    	
    	int currentOperation = planExecutor.getCurrentOperation();
		boolean operationNotInRange = planExecutor.getSearchPlan().getOperations().size() <= currentOperation || currentOperation < 0;
		ISearchOperation currentSerachOperation = operationNotInRange 
				? null 
				: planExecutor.getSearchPlan().getOperations().get(currentOperation);

		boolean matched = planExecutor.getSearchPlan().getOperations().size() == currentOperation;
		Object dummyMatchOperation = null;
		if(matched){
			dummyMatchOperation = operationListLabelProvider.getDummyMatchOperation(planExecutor);
		}
    	// TODO debug - why isn't the match found dummy op. detected? might not be registered for the correct planexecutor
        if (halted == false) {
            halted = breakpoints.contains(currentSerachOperation);
            halted |= breakpoints.contains(dummyMatchOperation);
        }        
        return halted;
    }
    
    @Override
    public void createPartControl(Composite parent) {
        parent.setLayoutData(new FillLayout());
        SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);

		planSashForm = new SashForm(sashForm, SWT.VERTICAL);

		// TreeViewer for the plan
		createTreeViewer(planSashForm);

        matchesTabFolder = new CTabFolder(planSashForm, SWT.MULTI);
        
        
        // Zest viewer
        createZestViewer(sashForm);
    }

    /**
     * Create the columns for the frame variables
     * 
     * @param colNames the variable names
     * @param parent the parent container
     * @param viewer the table viewer that will show the variable values
     */
	public void recreateColumns(List<String> colNames, TableViewer matchesViewer) {
		// TODO solve situations where the variable list changes (also in size)
		TableColumn[] columns = matchesViewer.getTable().getColumns();
		for (TableColumn tableColumn : columns) {
			tableColumn.dispose();
		}
		
		for (int i = 0; i < colNames.size(); i++) {
			TableViewerColumn col = createTableViewerColumn(colNames.get(i), 100, i, matchesViewer);
			col.setLabelProvider(new ColumnLabelProviderExtension(i));
		}

	}

	private TableViewerColumn createTableViewerColumn(String title, int bound, int colNumber, TableViewer matchesViewer) {
		TableViewerColumn viewerColumn = new TableViewerColumn(matchesViewer, SWT.NONE);
		TableColumn column = viewerColumn.getColumn();
		column.setText(title);
		column.setWidth(bound);
		column.setResizable(true);
		column.setMoveable(true);
		return viewerColumn;
	}
    
	private void createZestViewer(SashForm sashForm) {
        this.graphViewer = new GraphViewer(sashForm, SWT.BORDER);
        
        ZestNodeContentProvider zestContentProvider = new ZestNodeContentProvider();
        this.graphViewer.setContentProvider(zestContentProvider);
  
        ComposedAdapterFactory adapterFactory = new ComposedAdapterFactory(ComposedAdapterFactory.Descriptor.Registry.INSTANCE);
        adapterFactory.addAdapterFactory(new ReflectiveItemProviderAdapterFactory());
        AdapterFactoryLabelProvider labelProvider = new AdapterFactoryLabelProvider(adapterFactory);
        this.graphViewer.setLabelProvider(labelProvider);

        LayoutAlgorithm layout = getLayout();
        this.graphViewer.setLayoutAlgorithm(layout, true);
        this.graphViewer.applyLayout();
        this.graphViewer.setNodeStyle(ZestStyles.NONE);
        
        fillToolBar();
    }

    private void createTreeViewer(SashForm sashForm) {
        this.operationListViewer = new TreeViewer(sashForm, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);
        this.operationListContentProvider = new OperationListContentProvider();
        this.operationListLabelProvider = new OperationListLabelProvider(breakpoints);

        this.operationListViewer.setContentProvider(operationListContentProvider);
        operationListContentProvider.setLabelProvider(operationListLabelProvider);
        this.operationListViewer.setLabelProvider(operationListLabelProvider);
        this.operationListViewer.setInput(null);

        BreakPointListener breakPointListener = new BreakPointListener(this);
        this.operationListViewer.addDoubleClickListener(breakPointListener);
    }

    private LayoutAlgorithm getLayout() {
        LayoutAlgorithm layout;
        layout = new TreeLayoutAlgorithm();
        // layout = new GridLayoutAlgorithm();
        // layout = new SpringLayoutAlgorithm();
        // layout = new HorizontalTreeLayoutAlgorithm();
        // layout = new RadialLayoutAlgorithm();
        return layout;
    }

    private void fillToolBar() {
        ZoomContributionViewItem toolbarZoomContributionViewItem = new ZoomContributionViewItem(this);
        IActionBars bars = getViewSite().getActionBars();
        bars.getMenuManager().add(toolbarZoomContributionViewItem);
    }

    @Override
    public AbstractZoomableViewer getZoomableViewer() {
        return graphViewer;
    }

    @Override
    public void setFocus() {
        operationListViewer.getControl().setFocus();
    }

    public void refreshView() {
    	PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				operationListViewer.refresh();
				graphViewer.refresh();
				graphViewer.applyLayout();
				Collection<TableViewer> tableViewers = matchViewersMap.values();
				for (TableViewer tableViewer : tableViewers) {
					tableViewer.refresh();
				}
			}
		});
    }

    public TreeViewer getOperationListViewer() {
        return operationListViewer;
    }

    public void setOperationListViewer(TreeViewer operationListViewer) {
        this.operationListViewer = operationListViewer;
    }
    
    public OperationListContentProvider getOperationListContentProvider() {
    	return operationListContentProvider;
    }

    public OperationListLabelProvider getOperationListLabelProvider() {
        return operationListLabelProvider;
    }

    public void setOperationListLabelProvider(OperationListLabelProvider operationLabelProvider) {
        this.operationListLabelProvider = operationLabelProvider;
    }

    public GraphViewer getGraphViewer() {
        return graphViewer;
    }

    public ZestNodeContentProvider getZestContentProvider() {
        return zestContentProvider;
    }

    public void setHalted(boolean halted) {
        this.halted = halted;
    }

    public boolean isHalted() {
        return halted;
    }

	public TableViewer getMatchesViewer(String queryName) {
		TableViewer viewer = matchViewersMap.get(queryName);
		if(viewer == null){
			getOrCreateMatchesTab(queryName);
		}
		return matchViewersMap.get(queryName);
	}

	private void getOrCreateMatchesTab(final String tabTitle) {
		// This method is called from a non-ui thread so that a syncexec is required here
		PlatformUI.getWorkbench().getDisplay().syncExec(new Runnable() {
			@Override
			public void run() {
				CTabItem item = new CTabItem(matchesTabFolder, SWT.NULL);
				item.setText(tabTitle);		
				
				// Mark as active
				matchesTabFolder.setSelection(item);
				
				// Table viewer for the matches
				Composite container = new Composite(matchesTabFolder,SWT.NONE);
				container.setLayout(new FillLayout());
				final TableViewer viewer = createTableViewer(container);
				
				viewer.addSelectionChangedListener(new ISelectionChangedListener() {
					@Override
					public void selectionChanged(SelectionChangedEvent event) {
						if(event.getSelection() instanceof IStructuredSelection){
							IStructuredSelection selection = (IStructuredSelection) event.getSelection();
							MatchingFrame frame = (MatchingFrame) selection.getFirstElement();
							graphViewer.setInput(frame);
							graphViewer.applyLayout();
						}
					}
				});
				
				matchViewersMap.put(tabTitle, viewer);
				
				viewer.refresh();
				ArrayList<MatchingFrame> matchViewerInput = Lists.<MatchingFrame>newArrayList();
				viewer.setData(VIEWER_KEY, matchViewerInput);
				viewer.setInput(matchViewerInput);
				
				item.setControl(container);
				item.addListener(SWT.FOCUSED, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("focused detected");
						viewer.setSelection(null);
					}
				}); 
				item.addListener(SWT.FocusIn, new Listener() {
					
					@Override
					public void handleEvent(Event event) {
						// TODO Auto-generated method stub
						System.out.println("focus IN detected");
						viewer.setSelection(null);
					}
				}); 
			}
		});
		
	}

	
	private class MatchTableContentProvider implements IStructuredContentProvider {

		@Override
		public void dispose() {
			// nop
		}

		@Override
		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			// nop
		}

		@Override
		public Object[] getElements(Object inputElement) {
			if (inputElement instanceof Object[]) {
				return (Object[]) inputElement;
			}
			if (inputElement instanceof Collection) {
				return ((Collection<?>) inputElement).toArray();
			}
			return new Object[0];
		}

	}

	private TableViewer createTableViewer(Composite parent) {
		TableViewer matchesViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER | SWT.FULL_SELECTION);
    	
		matchesViewer.setContentProvider(new MatchTableContentProvider());

		GridData gridData = new GridData();
		gridData.verticalAlignment = GridData.FILL;
		gridData.grabExcessHorizontalSpace = true;
		gridData.grabExcessVerticalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		matchesViewer.getControl().setLayoutData(gridData);

		final Table table = matchesViewer.getTable();
		table.setHeaderVisible(true);
    	table.setLinesVisible(true); 
    	
    	return matchesViewer;
    	
	}


}
