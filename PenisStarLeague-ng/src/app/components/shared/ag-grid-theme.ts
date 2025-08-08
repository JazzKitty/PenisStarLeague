import { themeQuartz } from "ag-grid-community";

export class AgGridTheme{
    public static readonly theme = themeQuartz.withParams({
        backgroundColor: '#4E4E4E',
        foregroundColor: '#7EB900',
        headerTextColor: '#FFFFFF',
        headerBackgroundColor: '#424242',
        oddRowBackgroundColor: '#424242',
        headerColumnResizeHandleColor: '#7EB900',
        cellTextColor: '#FFFFFF',
    });
}

