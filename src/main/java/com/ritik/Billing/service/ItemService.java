package com.ritik.Billing.service;


import com.ritik.Billing.dto.item.ItemRequest;
import com.ritik.Billing.dto.item.ItemResponse;
import com.ritik.Billing.exception.ResourceNotFoundException;
import com.ritik.Billing.model.Item;
import com.ritik.Billing.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemResponse createItem(ItemRequest request) {
        Item item = new Item();
        item.setName(request.getName());
        item.setType(request.getType());
        item.setHsnSacCode(request.getHsnSacCode());
        item.setDescription(request.getDescription());
        item.setUnitPrice(request.getUnitPrice());
        item.setUnitOfMeasure(request.getUnitOfMeasure());
        item.setTaxable(request.isTaxable());
        item.setActive(true); // Default to active

        Item savedItem = itemRepository.save(item);
        return mapToItemResponse(savedItem);
    }

    @Transactional(readOnly = true)
    public ItemResponse getItemById(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));
        return mapToItemResponse(item);
    }

    @Transactional(readOnly = true)
    public List<ItemResponse> getAllItems(boolean includeInactive) {
        List<Item> items = includeInactive ? itemRepository.findAll() :
                itemRepository.findAll().stream().filter(Item::isActive).toList();
        return items.stream()
                .map(this::mapToItemResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ItemResponse updateItem(Long id, ItemRequest request) {
        Item existingItem = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));

        existingItem.setName(request.getName());
        existingItem.setDescription(request.getDescription());
        existingItem.setUnitPrice(request.getUnitPrice());
        existingItem.setUnitOfMeasure(request.getUnitOfMeasure());
        existingItem.setType(request.getType());
        existingItem.setHsnSacCode(request.getHsnSacCode());
        existingItem.setTaxable(request.isTaxable());
        // isActive will be managed by a separate method if needed, or included in request if partial update is allowed.

        Item updatedItem = itemRepository.save(existingItem);
        return mapToItemResponse(updatedItem);
    }

    @Transactional
    public void deleteItem(Long id) {
        Item item = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found with ID: " + id));
        // Perform soft delete
        item.setActive(false);
        itemRepository.save(item);
    }

    // Helper method to map Entity to Response DTO
    private ItemResponse mapToItemResponse(Item item) {
        ItemResponse response = new ItemResponse();
        response.setId(item.getId());
        response.setName(item.getName());
        response.setDescription(item.getDescription());
        response.setUnitPrice(item.getUnitPrice());
        response.setType(item.getType());
        response.setHsnSacCode(item.getHsnSacCode());
        response.setUnitOfMeasure(item.getUnitOfMeasure());
        response.setTaxable(item.isTaxable());
        response.setCreatedAt(item.getCreatedAt());
        response.setUpdatedAt(item.getUpdatedAt());
        response.setActive(item.isActive());
        return response;
    }
}
