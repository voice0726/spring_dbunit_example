package jp.voice0726.spring_junit_example.helper;

import com.github.springtestdbunit.dataset.ReplacementDataSetLoader;

public class ReplacementCsvDataSetLoader extends ReplacementDataSetLoader {
    public ReplacementCsvDataSetLoader() {
        super(new CsvDataSetLoader());
    }
}
