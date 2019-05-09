package com.monco.core.page;

import lombok.Data;

import java.io.Serializable;

/**
 * FileImportPage
 *
 * @author Lijin
 * @version 1.0.0
 */
@Data
public class FileImportPage implements Serializable {

    private static final long serialVersionUID = -1589158871832481778L;

    private String filePath;
}